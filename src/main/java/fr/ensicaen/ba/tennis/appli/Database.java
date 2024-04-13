package fr.ensicaen.ba.tennis.appli;

import fr.ensicaen.ba.tennis.entities.AdherentEntity;
import fr.ensicaen.ba.tennis.entities.InscriptionEntity;
import fr.ensicaen.ba.tennis.entities.TournoiEntity;
import jakarta.persistence.*;

import java.lang.reflect.Array;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Database {
    private EntityManager entityManager;

    public Database(){
        entityManager = Persistence.createEntityManagerFactory("TennisUnit").createEntityManager();
    }

    public ArrayList<TournoiEntity> getTournamentList () {
        Query query = entityManager.createQuery("from TournoiEntity");
        ArrayList<TournoiEntity> list = (ArrayList<TournoiEntity>) query.getResultList();
        return list;
    }

    public TournoiEntity getTournoi (int id) {
        Query query = entityManager.createQuery("from TournoiEntity where codetournoi = :id");
        query.setParameter("id", id);
        return (TournoiEntity) query.getSingleResult();
    }

    public ArrayList<AdherentEntity> getUsersInfo() {
        Query query = entityManager.createQuery("from AdherentEntity");
        ArrayList<AdherentEntity> usersInfo = (ArrayList<AdherentEntity>) query.getResultList();
        return usersInfo;
    }

    public Map<String, String> getUserInfo(String email) {
        Query query = entityManager.createQuery("SELECT a FROM AdherentEntity a WHERE a.email = :email");
        query.setParameter("email", email);
        try {
            AdherentEntity adherentEntity = (AdherentEntity) query.getSingleResult();
            Map<String, String> userInfos = new HashMap<>();
            userInfos.put("adherentNumber", String.valueOf(adherentEntity.getNumeroadherent()));
            userInfos.put("email", adherentEntity.getEmail());
            userInfos.put("name", adherentEntity.getPrenom());
            userInfos.put("surname", adherentEntity.getNom());
            String address = adherentEntity.getAdresse();
            if(address != null) {
                userInfos.put("address", address);
            } else {
                userInfos.put("address", "Adresse non disponible");
            }
            userInfos.put("phoneNumber", adherentEntity.getTelephone());
            return userInfos;
        } catch (NoResultException e) {
            return null;
        }
    }

    public String getUserSurnameFromEmail(String email) {
        Query query = entityManager.createQuery("from AdherentEntity where email = :email");
        query.setParameter("email", email);
        AdherentEntity adherentEntity = (AdherentEntity) query.getSingleResult();
        return adherentEntity.getNom();
    }

    public String getUserNameFromEmail(String email) {
        Query query = entityManager.createQuery("from AdherentEntity where email = :email");
        query.setParameter("email", email);
        AdherentEntity adherentEntity = (AdherentEntity) query.getSingleResult();
        return adherentEntity.getPrenom();
    }

    public String getUserAdherentNumberFromEmail(String email) {
        Query query = entityManager.createQuery("from AdherentEntity where email = :email");
        query.setParameter("email", email);
        AdherentEntity adherentEntity = (AdherentEntity) query.getSingleResult();
        return String.valueOf(adherentEntity.getNumeroadherent());
    }

    public ArrayList<TournoiEntity> getTournamentByAdherentEmail(String emailAdherent) {
        Query query = entityManager.createQuery(
                "SELECT t FROM TournoiEntity t " +
                        "JOIN InscriptionEntity i ON t.codetournoi = i.codetournoi " +
                        "JOIN AdherentEntity a ON a.numeroadherent = i.numeroadherent " +
                        "WHERE a.email = :email " +
                        "ORDER BY t.date", TournoiEntity.class);
        query.setParameter("email", emailAdherent);
        return (ArrayList<TournoiEntity>) query.getResultList();
    }

    public boolean signUserToTournament(String adherentNumber, String codeTournoi){
        try {
            entityManager.getTransaction().begin();

            InscriptionEntity inscription = new InscriptionEntity();
            inscription.setNumeroadherent(Integer.valueOf(adherentNumber));
            inscription.setCodetournoi(Integer.valueOf(codeTournoi));
            LocalDate today = LocalDate.now(ZoneId.systemDefault());
            java.sql.Date sqlDate = java.sql.Date.valueOf(today);
            inscription.setDateinscription(sqlDate);

            entityManager.persist(inscription);
            entityManager.getTransaction().commit();

            return true;
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            e.printStackTrace();
            return false;
        }
    }

    public boolean isSignedUpToTournament(String adherentNumber, String codeTournoi) {
        try {
            Query query = entityManager.createQuery(
                    "SELECT count(i) FROM InscriptionEntity i WHERE i.numeroadherent = :adherentNumber AND i.codetournoi = :codeTournoi");
            query.setParameter("adherentNumber", Integer.parseInt(adherentNumber));
            query.setParameter("codeTournoi", Integer.parseInt(codeTournoi));

            long count = (long) query.getSingleResult();
            return count > 0;
        } catch (NoResultException e) {
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean unsignUserToTournament(String adherentNumber, String codeTournoi) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Query query = entityManager.createQuery(
                    "DELETE FROM InscriptionEntity i WHERE i.codetournoi = :codeTournoi AND i.numeroadherent = :adherentNumber");
            query.setParameter("adherentNumber", Integer.parseInt(adherentNumber));
            query.setParameter("codeTournoi", Integer.parseInt(codeTournoi));
            int result = query.executeUpdate();
            transaction.commit();
            return result > 0;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }



}
