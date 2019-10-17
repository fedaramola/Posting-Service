package com.ecobank.postingservice.Service;

import com.ecobank.postingservice.Dao.AddParam;
import com.ecobank.postingservice.Dao.Resp;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleTypes;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Service
public class AddRecordCredit {
    public Resp AddTransactionCredit(Connection conn, AddParam addParam, String batchId) {

//        Connection conn = null;
        ResultSet Result = null;
        OracleCallableStatement cll = null;


        Integer resultout = null;

        String Output2 = null;
        String Output3 = null;
        String genbatch = null;
        Resp response = new Resp();

        List<Resp> Responses = new ArrayList<>();

        System.out.println("addParam: " + addParam.toString());
//        Batchid =response.getBatchno();


        try {
//            conn = Utilities.getConnection();
            conn = conn.unwrap(OracleConnection.class);
            cll = (OracleCallableStatement) conn.prepareCall("{?=call esb_fcc_instr_add_record(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
            int cnt1 = 0;

            System.out.println("##############################");
            System.out.println("Came after first add record============= 2");
            System.out.println("##############################");

            cll.registerOutParameter(++cnt1, OracleTypes.INTEGER);
            cll.setString(++cnt1, addParam.getPlatformid());
            cll.setString(++cnt1, addParam.getBranch());
            cll.setString(++cnt1, batchId);
//            cll.setString(++cnt1, genbatch);
            cll.setString(++cnt1, addParam.getReference());
            cll.setString(++cnt1, addParam.getCreditField().getAccountnum());
            cll.setString(++cnt1, addParam.getCreditField().getAccountbranch());
            cll.setString(++cnt1, addParam.getCreditField().getCrdrflag());
            cll.setInt(++cnt1, addParam.getCreditField().getTxnamount());
            cll.setString(++cnt1, addParam.getCreditField().getAmttype());
            cll.setString(++cnt1, addParam.getCreditField().getTxncurrency());
            cll.setDate(++cnt1, addParam.getInitdate());
            cll.setDate(++cnt1, addParam.getValuedate());
            cll.setInt(++cnt1, addParam.getCreditField().getSeqno());
            cll.setString(++cnt1, addParam.getCreditField().getTxndesc());
            cll.setInt(++cnt1, addParam.getCreditField().getExrate());
            cll.setString(++cnt1, addParam.getCreditField().getTrantype());
            cll.setString(++cnt1, addParam.getCreditField().getInstrumentno());
            cll.registerOutParameter(++cnt1, OracleTypes.VARCHAR);
            cll.registerOutParameter(++cnt1, OracleTypes.VARCHAR);

            cll.execute();


            resultout = (Integer) cll.getObject(1);
            Output3 = (String) cll.getObject(20);

            System.out.println("Output3 1: " + Output3);
            System.out.println("resultout 1: " + resultout);


            Output2 = (String) cll.getObject(19);
            Output3 = (String) cll.getObject(20);

            response.setResponsecode(Output2);
            response.setResponseMessage(Output3);

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