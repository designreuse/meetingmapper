package n.controller;

import n.dto.Meeting;
import n.dto.RaleighMeeting;
import n.service.GeocodeService;
import n.service.RaleighMeetingImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ImportController {

    @Autowired
    private RaleighMeetingImportService raleighMeetingImportService;
    @Autowired
    private GeocodeService geocodeService;

    @RequestMapping(value = "/import/raleigh")
    public ModelAndView importRaleigh() {
        //TODO: take source of file import as parameter, maybe an email to respond with confirmation too
        new Thread(new Runnable() {
            public void run() {
                List<RaleighMeeting> raleighMeetingList = raleighMeetingImportService.importFromLocalCsv();
                List<Meeting> meetings = raleighMeetingImportService.normalize(raleighMeetingList);
                for (final Meeting meeting : meetings) {
                    geocodeService.geocodeLocation(meeting.getLocation());
                }
            }
        }).start();
        return new ModelAndView("/import");
    }
}
