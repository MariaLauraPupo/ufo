package it.polito.tdp.ufo.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TestDB {

	public static void main(String[] args) {
         //passo 1 
         String jdbcURL = "jdbc:mysql://localhost/ufo_sightings?user=root&password=mari";
         //passo 2
         try {
			Connection conn=DriverManager.getConnection(jdbcURL);
			
			String sql =" SELECT DISTINCT shape " //ricordarsi gli spazi
					+ " FROM sighting "
					+ " WHERE shape <> '' "
					+ " ORDER BY shape ASC ";
			
		    //passo 3
			PreparedStatement st = conn.prepareStatement(sql);
			

			//mando la stringa al DB
			ResultSet res = st.executeQuery();
			
			//estraggo i risultati della tabella
			List<String> formeUfo= new ArrayList<String>();
			while(res.next()) {
				String forma = res.getString("shape"); //dammi sottoforma di stringa il dato che è presente nella colonna che si chiama shape
				formeUfo.add(forma);
			}
			System.out.println(formeUfo);
			
			//query da eseguire //questa query ha dei paramentri e li indico con un ?
			String sql2 =" SELECT COUNT(*) AS cnt "
					+" FROM sighting "
					+" WHERE shape= ? "; //metto un punto interrogativo il dato che deve inserire l'utente
			String shapeScelta = "circle" ;
			//creo lo statement e riceve la stringa contenente i parametri 
			PreparedStatement st2 = conn.prepareStatement(sql2);
			//imposto il valore del paramentro
			st2.setString(1,  shapeScelta); //1 indica il primo punto interrogativo(in questo caso ce ne è solo 1) e dico che valore andare a sostituire
			ResultSet res2 = st2.executeQuery();
			res2.first();
			int count = res2.getInt("cnt");
			st2.close();
			
			System.out.println("UFO di forma "+shapeScelta+" sono: "+count);
			
			conn.close();
         } catch (SQLException e) {
			e.printStackTrace();
		}
         
	}

}
