package n.controller;

import n.dto.Location;
import n.dto.RaleighMeeting;
import n.persistence.LocationDao;
import n.persistence.RaleighMeetingDao;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
public class MapController {

    @Autowired
    RaleighMeetingDao raleighMeetingDao;
    @Autowired
    LocationDao locationDao;

    @RequestMapping(value = "/")
    public ModelAndView index() {
        return new ModelAndView("/map");
    }

    @RequestMapping(value = "/locations")
    public
    @ResponseBody
    ResponseEntity<List<Location>> locations() {
        final List<Location> locations = locationDao.findAll();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=UTF-8");
        headers.add("Access-Control-Allow-Origin", "*");
        return (new ResponseEntity<List<Location>>(locations, headers, HttpStatus.OK));
    }

    @RequestMapping(value = "/insert")
    public ModelAndView insert() {
        RaleighMeeting raleighMeeting = new RaleighMeeting();
        raleighMeeting.setGroup("Los Borachos");
        raleighMeetingDao.save(raleighMeeting);

        return new ModelAndView("/insert");
    }
}
