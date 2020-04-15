package com.udesc.dcc.bdes.ciclogeoinforest.persistencia;

import com.udesc.dcc.bdes.ciclogeoinforest.dados.TransferObjects.Waypoint;
import com.udesc.dcc.bdes.ciclogeoinforest.dados.TransferObjects.Dificuldade;
import com.udesc.dcc.bdes.ciclogeoinforest.dados.TransferObjects.Usuario;
import com.udesc.dcc.bdes.ciclogeoinforest.dados.TransferObjects.Bairro;
import com.udesc.dcc.bdes.ciclogeoinforest.dados.TransferObjects.Trilhadados;
import com.udesc.dcc.bdes.ciclogeoinforest.dados.TransferObjects.Trilha;
import com.udesc.dcc.bdes.ciclogeoinforest.dados.TransferObjects.Regiao;
import com.udesc.dcc.bdes.ciclogeoinforest.dados.TransferObjects.Superficie;
import com.udesc.dcc.bdes.ciclogeoinforest.dados.TransferObjects.Tipo;
import com.udesc.dcc.bdes.ciclogeoinforest.persistencia.exceptions.IllegalOrphanException;
import com.udesc.dcc.bdes.ciclogeoinforest.persistencia.exceptions.NonexistentEntityException;
import com.udesc.dcc.bdes.ciclogeoinforest.persistencia.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.TemporalType;

/**
 *
 * @author Leandro
 */
public class TrilhadadosJpaController implements Serializable {

    public TrilhadadosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    /* create:
     * Insere os dados da trilha no banco. Se der erro, desfaz o que foi feito
     * Variáveis:
     *  int numPartes = guarda o numero de partes já inseridas
     *  int verif = para verificar o sucesso da inserção
     * Retornos:
     *  false = deu algum erro, todas as alterações foram desfeitas
     *  true = deu tudo certo */
    public boolean create(float comprimento, int desnivel, String nome,
            String descricao, int tip_cod, int dif_cod, int cid_cod, int usu_cod,
            ArrayList<String> geometria, ArrayList<Integer> bairros,
            ArrayList<Integer> regioes, ArrayList<Integer> superficies) {

        int verif;
        EntityManager em = null;

        try {
            //Insere dados da trilha

            String SQL1 = "INSERT INTO trilhadados (comprimento_km, desnivel, nome,"
                    + " descricao, tip_cod,"
                    + " dif_cod, cid_cod, usu_cod)"
                    + " VALUES ("
                    + comprimento + ", "
                    + desnivel + ", "
                    + "'" + nome + "', "
                    + "'" + descricao + "', "
                    + "'" + tip_cod + "', "
                    + "'" + dif_cod + "', "
                    + "'" + cid_cod + "', "
                    + "'" + usu_cod + "'"
                    + ") RETURNING codt";

            em = getEntityManager();
            em.getTransaction().begin();
            Query q = em.createNativeQuery(SQL1);
            Object o = q.getSingleResult();
            int codt = Integer.parseInt(o.toString());

            for (int cont = 0; cont < geometria.size(); cont++) {

                String SQL2 = "INSERT INTO trilha (codt, geometria) VALUES (" + codt + ", " + "ST_GeomFromText('LINESTRING(" + geometria.get(cont) + ")',4326))";
                verif = em.createNativeQuery(SQL2).executeUpdate();

                if (verif != 1) { //Se não retornar 1 significa que não inseriu ou deu algum outro erro

                    em.getTransaction().commit();
                    em.close();
                    return false;
                }

            }
            //Insere bairros
            for (int cont = 0; cont < bairros.size(); cont++) {

                String SQL3 = "INSERT INTO bairrotrilha VALUES (" + codt + ", " + bairros.get(cont) + ")";
                verif = em.createNativeQuery(SQL3).executeUpdate();

                if (verif != 1) { //Se não retornar 1 significa que não inseriu ou deu algum outro erro

                    em.getTransaction().commit();
                    em.close();
                    return false;

                }
            }
            //Insere regiões
            for (int cont = 0; cont < regioes.size(); cont++) {

                String SQL4 = "INSERT INTO regiaotrilha VALUES (" + codt + ", " + regioes.get(cont) + ")";
                verif = em.createNativeQuery(SQL4).executeUpdate();

                if (verif != 1) { //Se não retornar 1 significa que não inseriu ou deu algum outro erro

                    em.getTransaction().commit();
                    em.close();
                    return false;

                }
            }
            //Insere superfícies
            for (int cont = 0; cont < superficies.size(); cont++) {

                String SQL5 = "INSERT INTO superficietrilha VALUES (" + codt + ", " + superficies.get(cont) + ")";
                verif = em.createNativeQuery(SQL5).executeUpdate();

                if (verif != 1) { //Se não retornar 1 significa que não inseriu ou deu algum outro erro

                    em.getTransaction().commit();
                    em.close();
                    return false;

                }
            }
        } catch (Exception e) {
            System.out.println(e);
            em.getTransaction().commit();
            em.close();
            return false;
        }

        em.getTransaction().commit();
        em.close();
        return true;

    }
    
    /* create:
     * Insere os dados da trilha no banco. Se der erro, desfaz o que foi feito
     * Variáveis:
     *  int numPartes = guarda o numero de partes já inseridas
     *  int verif = para verificar o sucesso da inserção
     * Retornos:
     *  false = deu algum erro, todas as alterações foram desfeitas
     *  true = deu tudo certo */
    public boolean create(String comprimento, int desnivel, String nome,
            String descricao, byte[] graf_altitude,
            int tip_cod, int dif_cod, int cid_cod, int usu_cod,
            ArrayList<String> geometria, ArrayList<Integer> bairros,
            ArrayList<Integer> regioes, ArrayList<Integer> superficies) {
        int verif;
        EntityManager em = null;

        try {
            //Insere dados da trilha

            String SQL1 = "INSERT INTO trilhadados (comprimento_km, desnivel, nome,"
                    + " descricao, imgba, tip_cod,"
                    + " dif_cod, cid_cod)"
                    + " VALUES ("
                    + comprimento + ", "
                    + desnivel + ", "
                    + "'" + nome + "', "
                    + "'" + descricao + "', "
                    + "?1, "
                    + "'" + tip_cod + "', "
                    + "'" + dif_cod + "', "
                    + "'" + cid_cod
                    + "') RETURNING codt";

            em = getEntityManager();
            em.getTransaction().begin();
            Query q = em.createNativeQuery(SQL1).setParameter(1,graf_altitude);
            Object o = q.getSingleResult();
            int codt = Integer.parseInt(o.toString());
            for (int cont = 0; cont < geometria.size(); cont++) {

                String SQL2 = "INSERT INTO trilha (codt, geometria) VALUES (" + codt + ", " + "ST_GeomFromText('LINESTRING(" + geometria.get(cont) + ")',4326))";
                verif = em.createNativeQuery(SQL2).executeUpdate();
System.out.println(SQL2);
                if (verif != 1) { //Se não retornar 1 significa que não inseriu ou deu algum outro erro

                    em.getTransaction().commit();
                    em.close();
                    return false;
                }

            }
            //Insere bairros
            for (int cont = 0; cont < bairros.size(); cont++) {

                String SQL3 = "INSERT INTO bairrotrilha VALUES (" + codt + ", " + bairros.get(cont) + ")";
                verif = em.createNativeQuery(SQL3).executeUpdate();

                if (verif != 1) { //Se não retornar 1 significa que não inseriu ou deu algum outro erro

                    em.getTransaction().commit();
                    em.close();
                    return false;

                }
            }
            //Insere regiões
            for (int cont = 0; cont < regioes.size(); cont++) {

                String SQL4 = "INSERT INTO regiaotrilha VALUES (" + codt + ", " + regioes.get(cont) + ")";
                verif = em.createNativeQuery(SQL4).executeUpdate();

                if (verif != 1) { //Se não retornar 1 significa que não inseriu ou deu algum outro erro

                    em.getTransaction().commit();
                    em.close();
                    return false;

                }
            }
            //Insere superfícies
            for (int cont = 0; cont < superficies.size(); cont++) {

                String SQL5 = "INSERT INTO superficietrilha VALUES (" + codt + ", " + superficies.get(cont) + ")";
                verif = em.createNativeQuery(SQL5).executeUpdate();

                if (verif != 1) { //Se não retornar 1 significa que não inseriu ou deu algum outro erro

                    em.getTransaction().commit();
                    em.close();
                    return false;

                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            em.getTransaction().commit();
            em.close();
            return false;
        }

        em.getTransaction().commit();
        em.close();
        return true;

    }
    
    
     /* create:
     * Insere os dados da trilha no banco recebendo do android. Se der erro, desfaz o que foi feito
     * Variáveis:
     *  int numPartes = guarda o numero de partes já inseridas
     *  int verif = para verificar o sucesso da inserção
     * Retornos:
     *  false = deu algum erro, todas as alterações foram desfeitas
     *  true = deu tudo certo */
    public int create(String comprimento, int desnivel, String nome,
            String descricao,byte[] graf_altitude,
            int tip_cod, int dif_cod, int cid_cod, int usu_cod,
            ArrayList<String> geometria, ArrayList<Integer> bairros,
            ArrayList<Integer> regioes, ArrayList<Integer> superficies,
            String email, String timestampString, ArrayList<Double> altitudes) {
        int verif;
        EntityManager em = null;
        int codt;
        String valoresAltitudes = "";
        if(altitudes.size() > 0){
            valoresAltitudes = "{" + altitudes.get(0);
            for(int i = 1; i < altitudes.size(); i++){
                valoresAltitudes += ", " + altitudes.get(i);
            }
            valoresAltitudes += "}";
        }
        try {
            //Insere dados da trilha
            String SQL1 = "INSERT INTO trilhadados (comprimento_km, desnivel, nome,"
                    + " descricao, imgba, tip_cod,"
                    + " dif_cod, cid_cod, data_cadastro, altitudes)"
                    + " VALUES ("
                    + comprimento + ", "
                    + desnivel + ", "
                    + "'" + nome + "', "
                    + "'" + descricao + "', "
                    + "?2, "
                    + "'" + tip_cod + "', "
                    + "'" + dif_cod + "', "
                    + "'" + cid_cod + "', "
                    + "'" + timestampString + "', "
                    + "'" + valoresAltitudes + "'"
                    + ") RETURNING codt";
            em = getEntityManager();
            em.getTransaction().begin();
            Query q = em.createNativeQuery(SQL1);
            q.setParameter(2, graf_altitude);
            Object o = q.getSingleResult();
            codt = Integer.parseInt(o.toString());
            for (int cont = 0; cont < geometria.size(); cont++) {
                System.out.println(geometria.get(cont));
                String SQL2 = "INSERT INTO trilha (codt, geometria) VALUES (" + codt + ", " + "ST_GeomFromText('LINESTRING(" + geometria.get(cont) + ")',4326))";
                verif = em.createNativeQuery(SQL2).executeUpdate();
System.out.println(SQL2);
                if (verif != 1) { //Se não retornar 1 significa que não inseriu ou deu algum outro erro

                    em.getTransaction().commit();
                    em.close();
                    return -1;
                }

            }
            
            //insere usuario
            Usuario usu;
            usu = (Usuario) getEntityManager().createNamedQuery("Usuario.findByUsuLogin").setParameter("usuEmail", email).getSingleResult();
            String SQL_USUARIO = "INSERT INTO usuariotrilha (usu_cod, codt) VALUES ("+usu.getUsuCod()+","+codt+");";
            verif =  em.createNativeQuery(SQL_USUARIO).executeUpdate();
 System.out.println("SQL_USUARIO is "+ SQL_USUARIO);
 System.out.println("result is "+ verif);
            if (verif != 1) { //Se não retornar 1 significa que não inseriu ou deu algum outro erro

                    em.getTransaction().commit();
                    em.close();
                    return -1;

             }
             
            //Insere bairros
            for (int cont = 0; cont < bairros.size(); cont++) {

                String SQL3 = "INSERT INTO bairrotrilha VALUES (" + codt + ", " + bairros.get(cont) + ")";
                verif = em.createNativeQuery(SQL3).executeUpdate();

                if (verif != 1) { //Se não retornar 1 significa que não inseriu ou deu algum outro erro

                    em.getTransaction().commit();
                    em.close();
                    return -1;

                }
            }
            //Insere regiões
            for (int cont = 0; cont < regioes.size(); cont++) {

                String SQL4 = "INSERT INTO regiaotrilha VALUES (" + codt + ", " + regioes.get(cont) + ")";
                verif = em.createNativeQuery(SQL4).executeUpdate();

                if (verif != 1) { //Se não retornar 1 significa que não inseriu ou deu algum outro erro

                    em.getTransaction().commit();
                    em.close();
                    return -1;

                }
            }
            //Insere superfícies
            for (int cont = 0; cont < superficies.size(); cont++) {

                String SQL5 = "INSERT INTO superficietrilha VALUES (" + codt + ", " + superficies.get(cont) + ")";
                verif = em.createNativeQuery(SQL5).executeUpdate();

                if (verif != 1) { //Se não retornar 1 significa que não inseriu ou deu algum outro erro

                    em.getTransaction().commit();
                    em.close();
                    return -1;

                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            em.getTransaction().commit();
            em.close();
            return -1;
        }

        em.getTransaction().commit();
        em.close();
        return codt;

    }

    public void create(Trilhadados trilhadados) throws PreexistingEntityException, Exception {
        if (trilhadados.getRegiaoList() == null) {
            trilhadados.setRegiaoList(new ArrayList<Regiao>());
        }
        if (trilhadados.getBairrosList() == null) {
            trilhadados.setBairrosList(new ArrayList<Bairro>());
        }
        if (trilhadados.getSuperficieList() == null) {
            trilhadados.setSuperficieList(new ArrayList<Superficie>());
        }
        if (trilhadados.getTrilhaList() == null) {
            trilhadados.setTrilhaList(new ArrayList<Trilha>());
        }
        if (trilhadados.getWaypointList() == null) {
            trilhadados.setWaypointList(new ArrayList<Waypoint>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tipo tipCod = trilhadados.getTipCod();
            if (tipCod != null) {
                tipCod = em.getReference(tipCod.getClass(), tipCod.getTipCod());
                trilhadados.setTipCod(tipCod);
            }
            Dificuldade difCod = trilhadados.getDifCod();
            if (difCod != null) {
                difCod = em.getReference(difCod.getClass(), difCod.getDifCod());
                trilhadados.setDifCod(difCod);
            }
            List<Regiao> attachedRegiaoList = new ArrayList<Regiao>();
            for (Regiao regiaoListRegiaoToAttach : trilhadados.getRegiaoList()) {
                regiaoListRegiaoToAttach = em.getReference(regiaoListRegiaoToAttach.getClass(), regiaoListRegiaoToAttach.getRegCod());
                attachedRegiaoList.add(regiaoListRegiaoToAttach);
            }
            trilhadados.setRegiaoList(attachedRegiaoList);
            List<Bairro> attachedBairrosList = new ArrayList<Bairro>();
            for (Bairro bairrosListBairrosToAttach : trilhadados.getBairrosList()) {
                bairrosListBairrosToAttach = em.getReference(bairrosListBairrosToAttach.getClass(), bairrosListBairrosToAttach.getBaiCod());
                attachedBairrosList.add(bairrosListBairrosToAttach);
            }
            trilhadados.setBairrosList(attachedBairrosList);
            List<Superficie> attachedSuperficieList = new ArrayList<Superficie>();
            for (Superficie superficieListSuperficieToAttach : trilhadados.getSuperficieList()) {
                superficieListSuperficieToAttach = em.getReference(superficieListSuperficieToAttach.getClass(), superficieListSuperficieToAttach.getSupCod());
                attachedSuperficieList.add(superficieListSuperficieToAttach);
            }
            trilhadados.setSuperficieList(attachedSuperficieList);
            
            List<Trilha> attachedTrilhaList = new ArrayList<Trilha>();
            for (Trilha trilhaListTrilhaToAttach : trilhadados.getTrilhaList()) {
                trilhaListTrilhaToAttach = em.getReference(trilhaListTrilhaToAttach.getClass(), trilhaListTrilhaToAttach.getCodtParte());
                attachedTrilhaList.add(trilhaListTrilhaToAttach);
            }
            trilhadados.setTrilhaList(attachedTrilhaList);
            List<Waypoint> attachedWaypointList = new ArrayList<Waypoint>();
            for (Waypoint waypointListWaypointToAttach : trilhadados.getWaypointList()) {
                waypointListWaypointToAttach = em.getReference(waypointListWaypointToAttach.getClass(), waypointListWaypointToAttach.getCodwp());
                attachedWaypointList.add(waypointListWaypointToAttach);
            }
            trilhadados.setWaypointList(attachedWaypointList);
            em.persist(trilhadados);
            if (tipCod != null) {
                tipCod.getTrilhadadosList().add(trilhadados);
                tipCod = em.merge(tipCod);
            }
            if (difCod != null) {
                difCod.getTrilhadadosList().add(trilhadados);
                difCod = em.merge(difCod);
            }
            for (Regiao regiaoListRegiao : trilhadados.getRegiaoList()) {
                regiaoListRegiao.getTrilhadadosList().add(trilhadados);
                regiaoListRegiao = em.merge(regiaoListRegiao);
            }
            for (Bairro bairrosListBairros : trilhadados.getBairrosList()) {
                bairrosListBairros.getTrilhadadosList().add(trilhadados);
                bairrosListBairros = em.merge(bairrosListBairros);
            }
            for (Superficie superficieListSuperficie : trilhadados.getSuperficieList()) {
                superficieListSuperficie.getTrilhadadosList().add(trilhadados);
                superficieListSuperficie = em.merge(superficieListSuperficie);
            }
            for (Trilha trilhaListTrilha : trilhadados.getTrilhaList()) {
                Trilhadados oldCodtOfTrilhaListTrilha = trilhaListTrilha.getCodt();
                trilhaListTrilha.setCodt(trilhadados);
                trilhaListTrilha = em.merge(trilhaListTrilha);
                if (oldCodtOfTrilhaListTrilha != null) {
                    oldCodtOfTrilhaListTrilha.getTrilhaList().remove(trilhaListTrilha);
                    oldCodtOfTrilhaListTrilha = em.merge(oldCodtOfTrilhaListTrilha);
                }
            }
            for (Waypoint waypointListWaypoint : trilhadados.getWaypointList()) {
                Trilhadados oldCodtOfWaypointListWaypoint = waypointListWaypoint.getCodt();
                waypointListWaypoint.setCodt(trilhadados);
                waypointListWaypoint = em.merge(waypointListWaypoint);
                if (oldCodtOfWaypointListWaypoint != null) {
                    oldCodtOfWaypointListWaypoint.getWaypointList().remove(waypointListWaypoint);
                    oldCodtOfWaypointListWaypoint = em.merge(oldCodtOfWaypointListWaypoint);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTrilhadados(trilhadados.getCodt()) != null) {
                throw new PreexistingEntityException("Trilhadados " + trilhadados + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Trilhadados trilhadados) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Trilhadados persistentTrilhadados = em.find(Trilhadados.class, trilhadados.getCodt());
            Tipo tipCodOld = persistentTrilhadados.getTipCod();
            Tipo tipCodNew = trilhadados.getTipCod();
            Dificuldade difCodOld = persistentTrilhadados.getDifCod();
            Dificuldade difCodNew = trilhadados.getDifCod();
            List<Regiao> regiaoListOld = persistentTrilhadados.getRegiaoList();
            List<Regiao> regiaoListNew = trilhadados.getRegiaoList();
            List<Bairro> bairrosListOld = persistentTrilhadados.getBairrosList();
            List<Bairro> bairrosListNew = trilhadados.getBairrosList();
            List<Superficie> superficieListOld = persistentTrilhadados.getSuperficieList();
            List<Superficie> superficieListNew = trilhadados.getSuperficieList();
            List<Trilha> trilhaListOld = persistentTrilhadados.getTrilhaList();
            List<Trilha> trilhaListNew = trilhadados.getTrilhaList();
            List<Waypoint> waypointListOld = persistentTrilhadados.getWaypointList();
            List<Waypoint> waypointListNew = trilhadados.getWaypointList();
            List<String> illegalOrphanMessages = null;
            for (Trilha trilhaListOldTrilha : trilhaListOld) {
                if (!trilhaListNew.contains(trilhaListOldTrilha)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Trilha " + trilhaListOldTrilha + " since its codt field is not nullable.");
                }
            }
            for (Waypoint waypointListOldWaypoint : waypointListOld) {
                if (!waypointListNew.contains(waypointListOldWaypoint)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Waypoint " + waypointListOldWaypoint + " since its codt field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (tipCodNew != null) {
                tipCodNew = em.getReference(tipCodNew.getClass(), tipCodNew.getTipCod());
                trilhadados.setTipCod(tipCodNew);
            }
            if (difCodNew != null) {
                difCodNew = em.getReference(difCodNew.getClass(), difCodNew.getDifCod());
                trilhadados.setDifCod(difCodNew);
            }
            List<Regiao> attachedRegiaoListNew = new ArrayList<Regiao>();
            for (Regiao regiaoListNewRegiaoToAttach : regiaoListNew) {
                regiaoListNewRegiaoToAttach = em.getReference(regiaoListNewRegiaoToAttach.getClass(), regiaoListNewRegiaoToAttach.getRegCod());
                attachedRegiaoListNew.add(regiaoListNewRegiaoToAttach);
            }
            regiaoListNew = attachedRegiaoListNew;
            trilhadados.setRegiaoList(regiaoListNew);
            List<Bairro> attachedBairrosListNew = new ArrayList<Bairro>();
            for (Bairro bairrosListNewBairrosToAttach : bairrosListNew) {
                bairrosListNewBairrosToAttach = em.getReference(bairrosListNewBairrosToAttach.getClass(), bairrosListNewBairrosToAttach.getBaiCod());
                attachedBairrosListNew.add(bairrosListNewBairrosToAttach);
            }
            bairrosListNew = attachedBairrosListNew;
            trilhadados.setBairrosList(bairrosListNew);
            List<Superficie> attachedSuperficieListNew = new ArrayList<Superficie>();
            for (Superficie superficieListNewSuperficieToAttach : superficieListNew) {
                superficieListNewSuperficieToAttach = em.getReference(superficieListNewSuperficieToAttach.getClass(), superficieListNewSuperficieToAttach.getSupCod());
                attachedSuperficieListNew.add(superficieListNewSuperficieToAttach);
            }
            superficieListNew = attachedSuperficieListNew;
            trilhadados.setSuperficieList(superficieListNew);
            List<Trilha> attachedTrilhaListNew = new ArrayList<Trilha>();
            for (Trilha trilhaListNewTrilhaToAttach : trilhaListNew) {
                trilhaListNewTrilhaToAttach = em.getReference(trilhaListNewTrilhaToAttach.getClass(), trilhaListNewTrilhaToAttach.getCodtParte());
                attachedTrilhaListNew.add(trilhaListNewTrilhaToAttach);
            }
            trilhaListNew = attachedTrilhaListNew;
            trilhadados.setTrilhaList(trilhaListNew);
            List<Waypoint> attachedWaypointListNew = new ArrayList<Waypoint>();
            for (Waypoint waypointListNewWaypointToAttach : waypointListNew) {
                waypointListNewWaypointToAttach = em.getReference(waypointListNewWaypointToAttach.getClass(), waypointListNewWaypointToAttach.getCodwp());
                attachedWaypointListNew.add(waypointListNewWaypointToAttach);
            }
            waypointListNew = attachedWaypointListNew;
            trilhadados.setWaypointList(waypointListNew);
            trilhadados = em.merge(trilhadados);
            if (tipCodOld != null && !tipCodOld.equals(tipCodNew)) {
                tipCodOld.getTrilhadadosList().remove(trilhadados);
                tipCodOld = em.merge(tipCodOld);
            }
            if (tipCodNew != null && !tipCodNew.equals(tipCodOld)) {
                tipCodNew.getTrilhadadosList().add(trilhadados);
                tipCodNew = em.merge(tipCodNew);
            }
            if (difCodOld != null && !difCodOld.equals(difCodNew)) {
                difCodOld.getTrilhadadosList().remove(trilhadados);
                difCodOld = em.merge(difCodOld);
            }
            if (difCodNew != null && !difCodNew.equals(difCodOld)) {
                difCodNew.getTrilhadadosList().add(trilhadados);
                difCodNew = em.merge(difCodNew);
            }
            for (Regiao regiaoListOldRegiao : regiaoListOld) {
                if (!regiaoListNew.contains(regiaoListOldRegiao)) {
                    regiaoListOldRegiao.getTrilhadadosList().remove(trilhadados);
                    regiaoListOldRegiao = em.merge(regiaoListOldRegiao);
                }
            }
            for (Regiao regiaoListNewRegiao : regiaoListNew) {
                if (!regiaoListOld.contains(regiaoListNewRegiao)) {
                    regiaoListNewRegiao.getTrilhadadosList().add(trilhadados);
                    regiaoListNewRegiao = em.merge(regiaoListNewRegiao);
                }
            }
            for (Bairro bairrosListOldBairros : bairrosListOld) {
                if (!bairrosListNew.contains(bairrosListOldBairros)) {
                    bairrosListOldBairros.getTrilhadadosList().remove(trilhadados);
                    bairrosListOldBairros = em.merge(bairrosListOldBairros);
                }
            }
            for (Bairro bairrosListNewBairros : bairrosListNew) {
                if (!bairrosListOld.contains(bairrosListNewBairros)) {
                    bairrosListNewBairros.getTrilhadadosList().add(trilhadados);
                    bairrosListNewBairros = em.merge(bairrosListNewBairros);
                }
            }
            for (Superficie superficieListOldSuperficie : superficieListOld) {
                if (!superficieListNew.contains(superficieListOldSuperficie)) {
                    superficieListOldSuperficie.getTrilhadadosList().remove(trilhadados);
                    superficieListOldSuperficie = em.merge(superficieListOldSuperficie);
                }
            }
            for (Superficie superficieListNewSuperficie : superficieListNew) {
                if (!superficieListOld.contains(superficieListNewSuperficie)) {
                    superficieListNewSuperficie.getTrilhadadosList().add(trilhadados);
                    superficieListNewSuperficie = em.merge(superficieListNewSuperficie);
                }
            }
            
            for (Trilha trilhaListNewTrilha : trilhaListNew) {
                if (!trilhaListOld.contains(trilhaListNewTrilha)) {
                    Trilhadados oldCodtOfTrilhaListNewTrilha = trilhaListNewTrilha.getCodt();
                    trilhaListNewTrilha.setCodt(trilhadados);
                    trilhaListNewTrilha = em.merge(trilhaListNewTrilha);
                    if (oldCodtOfTrilhaListNewTrilha != null && !oldCodtOfTrilhaListNewTrilha.equals(trilhadados)) {
                        oldCodtOfTrilhaListNewTrilha.getTrilhaList().remove(trilhaListNewTrilha);
                        oldCodtOfTrilhaListNewTrilha = em.merge(oldCodtOfTrilhaListNewTrilha);
                    }
                }
            }
            for (Waypoint waypointListNewWaypoint : waypointListNew) {
                if (!waypointListOld.contains(waypointListNewWaypoint)) {
                    Trilhadados oldCodtOfWaypointListNewWaypoint = waypointListNewWaypoint.getCodt();
                    waypointListNewWaypoint.setCodt(trilhadados);
                    waypointListNewWaypoint = em.merge(waypointListNewWaypoint);
                    if (oldCodtOfWaypointListNewWaypoint != null && !oldCodtOfWaypointListNewWaypoint.equals(trilhadados)) {
                        oldCodtOfWaypointListNewWaypoint.getWaypointList().remove(waypointListNewWaypoint);
                        oldCodtOfWaypointListNewWaypoint = em.merge(oldCodtOfWaypointListNewWaypoint);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = trilhadados.getCodt();
                if (findTrilhadados(id) == null) {
                    throw new NonexistentEntityException("The trilhadados with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public boolean delete(int codt) {

        EntityManager em = null;
        em = getEntityManager();
        em.getTransaction().begin();

        int success = em.createNativeQuery("DELETE FROM trilhadados WHERE codt = " + codt).executeUpdate();

        em.getTransaction().commit();
        em.close();

        if(success == 1){
            return true;
        } else {
            return false;
        }
        
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Trilhadados trilhadados;
            try {
                trilhadados = em.getReference(Trilhadados.class, id);
                trilhadados.getCodt();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The trilhadados with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Trilha> trilhaListOrphanCheck = trilhadados.getTrilhaList();
            for (Trilha trilhaListOrphanCheckTrilha : trilhaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Trilhadados (" + trilhadados + ") cannot be destroyed since the Trilha " + trilhaListOrphanCheckTrilha + " in its trilhaList field has a non-nullable codt field.");
            }
            List<Waypoint> waypointListOrphanCheck = trilhadados.getWaypointList();
            for (Waypoint waypointListOrphanCheckWaypoint : waypointListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Trilhadados (" + trilhadados + ") cannot be destroyed since the Waypoint " + waypointListOrphanCheckWaypoint + " in its waypointList field has a non-nullable codt field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Tipo tipCod = trilhadados.getTipCod();
            if (tipCod != null) {
                tipCod.getTrilhadadosList().remove(trilhadados);
                tipCod = em.merge(tipCod);
            }
            Dificuldade difCod = trilhadados.getDifCod();
            if (difCod != null) {
                difCod.getTrilhadadosList().remove(trilhadados);
                difCod = em.merge(difCod);
            }
            List<Regiao> regiaoList = trilhadados.getRegiaoList();
            for (Regiao regiaoListRegiao : regiaoList) {
                regiaoListRegiao.getTrilhadadosList().remove(trilhadados);
                regiaoListRegiao = em.merge(regiaoListRegiao);
            }
            List<Bairro> bairrosList = trilhadados.getBairrosList();
            for (Bairro bairrosListBairros : bairrosList) {
                bairrosListBairros.getTrilhadadosList().remove(trilhadados);
                bairrosListBairros = em.merge(bairrosListBairros);
            }
            List<Superficie> superficieList = trilhadados.getSuperficieList();
            for (Superficie superficieListSuperficie : superficieList) {
                superficieListSuperficie.getTrilhadadosList().remove(trilhadados);
                superficieListSuperficie = em.merge(superficieListSuperficie);
            }
            
            em.remove(trilhadados);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Trilhadados> findTrilhadadosEntities() {
        return findTrilhadadosEntities(true, -1, -1);
    }

    public List<Trilhadados> findTrilhadadosEntities(int maxResults, int firstResult) {
        return findTrilhadadosEntities(false, maxResults, firstResult);
    }

    private List<Trilhadados> findTrilhadadosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Trilhadados as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Trilhadados findTrilhadados(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Trilhadados.class, id);
        } finally {
            em.close();
        }
    }

    public int getTrilhadadosCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Trilhadados as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
}
