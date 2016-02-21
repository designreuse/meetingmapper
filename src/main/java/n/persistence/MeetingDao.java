package n.persistence;

import n.dto.Location;
import n.dto.Meeting;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class MeetingDao extends AbstractJpaDao<Meeting> {
    private static final String TABLE = "meeting";

    public MeetingDao() {
        setClazz(Meeting.class);
    }

    public Meeting findByLocationNameDayAndTime(final Long locationId, final String name, final String day, final String time) {
        final Query query = this.entityManager.createNativeQuery("select * from meeting where location_id = ? and name = ? and day = ? and time = ?", Meeting.class);
        query.setParameter(1, locationId);
        query.setParameter(2, name);
        query.setParameter(3, day);
        query.setParameter(4, time);
        final List<Meeting> meetingsFound = query.getResultList();
        if (!meetingsFound.isEmpty()) {
            if (meetingsFound.size() > 0) {
                //todo: log warning... or maybe error!
            }
            return meetingsFound.get(0);
        }
        return null;
    }

    public void truncate() {
        truncate(TABLE);
    }

}
