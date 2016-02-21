package n.service;

import au.com.bytecode.opencsv.CSVReader;
import n.dto.Location;
import n.dto.Meeting;
import n.dto.RaleighMeeting;
import n.persistence.LocationDao;
import n.persistence.MeetingDao;
import n.persistence.RaleighMeetingDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class RaleighMeetingImportService {
    @Autowired
    private RaleighMeetingDao raleighMeetingDao;
    @Autowired
    private LocationDao locationDao;
    @Autowired
    private MeetingDao meetingDao;
    private static final String RALEIGH_MEETING_FILE = "C:\\code\\meetingmapper\\src\\main\\resources\\raleigh_meetings.csv";
    private static final String RALEIGH_MEETING_URL = "http://raleighaa.com/download_csv.php";

    //TODO: handle meetings that have been removed from raleigh list

    @Transactional
    public List<RaleighMeeting> importFromLocalCsv() {

        try {
            final List<RaleighMeeting> raleighMeetings = new ArrayList();

            final URL url = new URL(RALEIGH_MEETING_URL);
            BufferedReader brUrl = new BufferedReader(new InputStreamReader(url.openStream()));
            CSVReader reader = new CSVReader(brUrl);
//            final CSVReader reader = new CSVReader(new FileReader(RALEIGH_MEETING_FILE));

            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                if (nextLine[0].equals("Day")) {
                    continue;
                }
                final RaleighMeeting raleighMeeting = new RaleighMeeting();
                raleighMeeting.setDay(nextLine[0]);
                raleighMeeting.setTime(nextLine[1]);
                raleighMeeting.setGroup(nextLine[2]);
                raleighMeeting.setLocation(nextLine[3]);
                raleighMeeting.setAddress(nextLine[4]);
                raleighMeeting.setCity(nextLine[5]);
                raleighMeetings.add(raleighMeeting);
                raleighMeetingDao.save(raleighMeeting);
            }
            raleighMeetingDao.truncate();
            for (final RaleighMeeting raleighMeeting : raleighMeetings) {
                raleighMeetingDao.save(raleighMeeting);
            }
            return raleighMeetings;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Transactional
    public List<Meeting> normalize(final List<RaleighMeeting> raleighMeetings) {
        //TODO: normalize times
        final List<Meeting> meetings = new ArrayList();
        for (final RaleighMeeting raleighMeeting : raleighMeetings) {
            final Location foundLocation = locationDao.findByAddress(raleighMeeting.getAddress());
            if (foundLocation != null) {
                //TODO: this will change when time is normalized
                final Meeting foundMeeting = meetingDao.findByLocationNameDayAndTime(foundLocation.getId(), raleighMeeting.getGroup(), raleighMeeting.getDay(), raleighMeeting.getTime());
                if (foundMeeting != null) {
                    //TODO: update notes
                    continue;
                }
                final Meeting meeting = new Meeting();
                meeting.setName(raleighMeeting.getGroup());
                meeting.setDay(raleighMeeting.getDay());
                meeting.setLocation(foundLocation);
                //todo: add notes
                meeting.setNotes(null);
                meeting.setTime(raleighMeeting.getTime());
                //todo: add type
                meeting.setType(null);
                meetingDao.save(meeting);
                meetings.add(meeting);
                continue;
            }

            final Location newLocation = new Location();
            newLocation.setAddress(raleighMeeting.getAddress());
            newLocation.setCity(raleighMeeting.getCity());
            newLocation.setName(raleighMeeting.getLocation());
            //todo: geocode now or later?
            locationDao.save(newLocation);

            final Meeting meeting = new Meeting();
            meeting.setName(raleighMeeting.getGroup());
            meeting.setDay(raleighMeeting.getDay());
            meeting.setLocation(newLocation);
            //todo: add notes
            meeting.setNotes(null);
            meeting.setTime(raleighMeeting.getTime());
            //todo: add type
            meeting.setType(null);
            meetingDao.save(meeting);
            meetings.add(meeting);
        }
        return meetings;
    }
}
