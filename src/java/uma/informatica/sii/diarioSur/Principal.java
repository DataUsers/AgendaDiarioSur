
package uma.informatica.sii.diarioSur;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author francis
 */
public class Principal {

    public static void main(String[] string) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("AgendaDiarioSurPU");
        EntityManager em = emf.createEntityManager();

        em.close();
        emf.close();

    }

}