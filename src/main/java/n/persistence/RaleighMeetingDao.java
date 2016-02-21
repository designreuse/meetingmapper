package n.persistence;

import n.dto.RaleighMeeting;
import org.springframework.stereotype.Repository;

@Repository
public class RaleighMeetingDao extends AbstractJpaDao<RaleighMeeting> {
    private static final String TABLE = "raleigh_meeting";

    public RaleighMeetingDao() {
        setClazz(RaleighMeeting.class);
    }

    public void truncate() {
        truncate(TABLE);
    }

}
