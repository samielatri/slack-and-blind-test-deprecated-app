package database;

import java.sql.*;

public class Reponse {
    private String rep;
    Connection conn = ConnectionBuilder.createConnection();
    Statement state = conn.createStatement();
    ResultSet res=null, res1=null;


    public Reponse() throws SQLException {
    }

    public boolean dbRep(String q, String askR){
        String reponseDB="";
        int ans=0;
        try {
            String first="SELECT * FROM question WHERE question=?";
            PreparedStatement pstate= conn.prepareStatement(first);
            pstate.setString(1,q);
            res1=pstate.executeQuery();
            while (res1.next()){
                ans=res1.getInt("idQ");
            }
            String sql= "SELECT * FROM reponse WHERE idQ=?";
            PreparedStatement pstate2= conn.prepareStatement(sql);
            pstate2.setInt(1,ans);
            res= pstate2.executeQuery();
            while(res.next()){
                reponseDB= res.getString("reponseQ");
                if(askR.equals(reponseDB)){
                    return true;
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
