package com.ecobank.postingservice.Service;

import com.ecobank.postingservice.Dao.AddParam;
import com.ecobank.postingservice.Dao.Resp;
import com.ecobank.postingservice.Entity.Utilities;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleTypes;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostTransaction {
    public Resp PostToFlexcube(Connection conn, AddParam addParam ,String batchId) {
        ResultSet rs = null;
        OracleCallableStatement cll = null;
//        Connection conn = null;
        String genbatch = null;
        String Output5 = null;
        String Output6 = null;
        Integer resultout = null;
        List<Resp> Responses = new ArrayList<>();

        Resp response = new Resp();
//        Batchid =response.getBatchno();

        try {
//            conn = Utilities.getConnection();
            conn = conn.unwrap(OracleConnection.class);
            cll = (OracleCallableStatement) conn.prepareCall("{?=call esb_fcc_post_txn(?,?,?,?,?,?)}");
            cll.registerOutParameter(1, OracleTypes.INTEGER);
            cll.setString(2, addParam.getReference());
            cll.setString(3, addParam.getPlatformid());
            cll.setString(4, addParam.getBranch());
            cll.setString(5, batchId);
//            cll.setString(5, genbatch);
            cll.registerOutParameter(6, OracleTypes.VARCHAR);
            cll.registerOutParameter(7, OracleTypes.VARCHAR);

            System.out.println("================================genbatch");
            System.out.println("About to post to flexcube");
            System.out.println("=================================");

            cll.execute();

            resultout = (Integer) cll.getObject(1);
            Output5 = (String) cll.getObject(6);
            Output6 = (String) cll.getObject(7);

            System.out.println("resultoutFinal: " + resultout);
            System.out.println("Output5 : " + Output5 + " Output6: " + Output6);


            response.setResponsecode(Output5);
            response.setResponseMessage(Output6);

        } catch (Exception exception) {
            exception.printStackTrace();
        }
//        finally {
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
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
        return response;
    }

}
