package com.ecobank.postingservice.Service;

import com.ecobank.postingservice.Dao.AddParam;
import com.ecobank.postingservice.Dao.Resp;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleTypes;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

@Service
public class Initmethod {
    public Resp TransactionInitiation(Connection conn, AddParam addParam) {
       // Connection conn = null;
        ResultSet Result = null;
        OracleCallableStatement stmt = null;


        Integer resultout = null;

        String Output2 = null;
        String Output3 = null;
        String genbatch = null;
        Resp response = new Resp();


        List<Resp> Responses = new ArrayList<>();


        System.out.println("addParam: " + addParam.toString());

        try {
            //conn = Utilities.getConnection();
            conn = conn.unwrap(OracleConnection.class);

            System.out.println("##############################");
            System.out.println("About to initiate transaction");
            System.out.println("##############################");

            stmt = (OracleCallableStatement) conn.prepareCall("{?=call esb_fcc_init_txn(?,?,?,?,?,?,?,?,?,?,?,?)}");
            int counts = 0;

            stmt.registerOutParameter(++counts, OracleTypes.INTEGER);
            stmt.setString(++counts, addParam.getReference());
            stmt.setString(++counts, addParam.getEvent());
            stmt.setString(++counts, addParam.getPlatformid());
            stmt.setString(++counts, addParam.getClientIp());
            stmt.setString(++counts, addParam.getBranch());
            stmt.setString(++counts, addParam.getDebitField().getAccountnum());
            stmt.setInt(++counts, addParam.getDebitField().getTxnamount());
            stmt.setString(++counts, addParam.getDebitField().getCrdrflag());
            stmt.registerOutParameter(++counts, Types.DATE);
            stmt.registerOutParameter(++counts, OracleTypes.VARCHAR);
            stmt.registerOutParameter(++counts, OracleTypes.VARCHAR);
            stmt.registerOutParameter(++counts, OracleTypes.VARCHAR);


            stmt.execute();


            resultout = (Integer) stmt.getObject(1);

            Output2 = (String) stmt.getObject(12);
            Output3 = (String) stmt.getObject(13);
            genbatch = (String) stmt.getObject(11);

            System.out.println("===========genbatch++++++++++" + genbatch);



            System.out.println("Output " + resultout);
            System.out.println("Intiate : " + Output3);

            response.setResponsecode(Output2);
            response.setResponseMessage(Output3);
            response.setBatchno(genbatch);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        } finally {
//            try {
//                if (conn != null) {
//                    conn.close();
//                }
//                if (Result != null) {
//                    Result.close();
//                }
//                if (stmt != null) {
//                    stmt.close();
//                }
//            } catch (Exception e1) {
//                e1.printStackTrace();
//            }
//
//        }
        return response;
    }
}