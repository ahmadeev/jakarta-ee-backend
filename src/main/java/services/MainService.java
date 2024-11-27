package services;

import dto.DragonDTO;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import objects.*;
import jakarta.persistence.Query;

import java.util.List;

@Named(value = "mainService")
@ApplicationScoped
public class MainService {

    @PersistenceContext
    protected EntityManager em;

    @PostConstruct
    private void init() {
        System.out.println("MainService initialized");
    }

    @Transactional
    public void createDragon(Dragon dragon, long userId) {
        dragon.setOwnerId(userId);
        em.persist(dragon);
    }

    @Transactional
    public Dragon getDragonById(long id) {
        return em.find(Dragon.class, id);
    }

    @Transactional
    public List<Dragon> getDragons(int page, int size) {
        return em.createQuery("SELECT i FROM Dragon i", Dragon.class)
                .setFirstResult(page * size)
                .setMaxResults(size)
                .getResultList();
    }

    @Transactional
    public boolean deleteDragonById(long id, long userId) {
        Dragon dragon = em.find(Dragon.class, id);

        if (dragon == null) return false;
        if (dragon.getOwnerId() != userId) return false;

        em.remove(dragon);
        return true;
    }

    @Transactional
    public int deleteDragons(long userId) {
        String jpql = "DELETE FROM Dragon o WHERE o.ownerId = :userId";
        Query query = em.createQuery(jpql);
        query.setParameter("userId", userId);
        return query.executeUpdate();
    }

    public Dragon createEntityFromDTO(DragonDTO dto) {
        var coordinates = dto.getCoordinates();
        var cave = dto.getCave();
        var killer = dto.getKiller();
        var location = killer.getLocation();
        var head = dto.getHead();

        return new Dragon(
                dto.getName(),
                new Coordinates(
                        coordinates.getX(),
                        coordinates.getY()
                ),
                dto.getCreationDate(),
                new DragonCave(
                        cave.getNumberOfTreasures()
                ),
                new Person(
                        killer.getName(),
                        killer.getEyeColor(),
                        killer.getHairColor(),
                        new Location(
                                location.getX(),
                                location.getY(),
                                location.getZ()
                        ),
                        killer.getBirthday(),
                        killer.getHeight()
                ),
                dto.getAge(),
                dto.getDescription(),
                dto.getWingspan(),
                dto.getCharacter(),
                new DragonHead(
                        head.getEyesCount(),
                        head.getToothCount()
                )
        );
    }
}
