package n.persistence;

import n.dto.Location;
import n.dto.RaleighMeeting;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class LocationDao extends AbstractJpaDao<Location> {
    private static final String TABLE = "location";

    public LocationDao() {
        setClazz(Location.class);
    }

    public Location findByAddress(final String address) {
        final Query query = this.entityManager.createNativeQuery("select * from location where address = ?", Location.class);
        query.setParameter(1, address);
        final List<Location> locationsFound = query.getResultList();
        if (!locationsFound.isEmpty()) {
            if (locationsFound.size() > 0) {
                //todo: log warning... or maybe error!
            }
            return locationsFound.get(0);
        }
        return null;
    }

    public void truncate() {
        truncate(TABLE);
    }

}
