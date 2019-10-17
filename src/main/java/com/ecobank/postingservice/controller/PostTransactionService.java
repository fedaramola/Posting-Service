package com.ecobank.postingservice.controller;

import com.ecobank.postingservice.Dao.AddParam;
import com.ecobank.postingservice.Dao.PostingResponse;
import com.ecobank.postingservice.Dao.Resp;
import com.ecobank.postingservice.Entity.Utilities;
import com.ecobank.postingservice.Service.AddRecordCredit;
import com.ecobank.postingservice.Service.AddRecordDebit;
import com.ecobank.postingservice.Service.Initmethod;
import com.ecobank.postingservice.Service.PostTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;

@RestController
public class PostTransactionService {


    @Autowired
    private Initmethod initmethod;
    @Autowired
    private AddRecordDebit addRecordDebit;

    @Autowired
    private AddRecordCredit addRecordCredit;

    @Autowired
    private PostTransaction postTransaction;

    PostingResponse postingResponse = new PostingResponse();

//    @Autowired
//    public PostTransactionService(Initmethod initmethod){
//        this.initmethod = initmethod;
//    }

    @PostMapping(value = "/Api/PostingService", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> PostRecord(@RequestBody AddParam addParam) {
        Connection connection = null;
        try {
            connection = Utilities.getConnection();

            Resp resp = initmethod.TransactionInitiation(connection, addParam);

            postingResponse.setResponsecode(resp.getResponsecode());
            postingResponse.setResponseMessage(resp.getResponseMessage());

            System.out.println("resp.getBatchno(): "+resp.getBatchno());

            if (resp.getResponsecode().equals("00")) {

                Resp responseOfDebit = addRecordDebit.AddTransactionDebit(connection, addParam, resp.getBatchno());

                postingResponse.setResponsecode(responseOfDebit.getResponsecode());
                postingResponse.setResponseMessage(responseOfDebit.getResponseMessage());

                if (responseOfDebit.getResponsecode().equals("00")) {

                    Resp responseOfCredit = addRecordCredit.AddTransactionCredit(connection, addParam, resp.getBatchno());

                    postingResponse.setResponsecode(responseOfCredit.getResponsecode());
                    postingResponse.setResponseMessage(responseOfCredit.getResponseMessage());

                    if (responseOfCredit.getResponsecode().equals("00")) {

                        Resp flexcuberesponse = postTransaction.PostToFlexcube(connection, addParam, resp.getBatchno());

                        postingResponse.setResponsecode(flexcuberesponse.getResponsecode());
                        postingResponse.setResponseMessage(flexcuberesponse.getResponseMessage());

                    } else {
                        System.out.println("Add record Credit Exception");

                        postingResponse.setResponsecode(responseOfCredit.getResponsecode());
                        postingResponse.setResponseMessage(responseOfCredit.getResponseMessage());

                    }

                } else {
                    System.out.println("Add record debit exception ");

                    postingResponse.setResponsecode(responseOfDebit.getResponsecode());
                    postingResponse.setResponseMessage(responseOfDebit.getResponseMessage());

                }

            } else {
                System.out.println("Initiate exception");

                postingResponse.setResponsecode(resp.getResponsecode());
                postingResponse.setResponseMessage(resp.getResponseMessage());

            }


        } catch (Exception e) {

        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }

        }

        return ResponseEntity.status(HttpStatus.OK).body(postingResponse);
    }


//    @PostMapping(value = "/PostingTxn", produces = MediaType.APPLICATION_JSON_VALUE)
//
//    public Integer initialRecord(@RequestBody initParameter record) {
//
//        Connection con = null;
//        ResultSet rs = null;
//        OracleCallableStatement cll = null;
//        Integer Output = null;
//        String Output1 = null;
//        String Output2 = null;
//        String Output3 = null;
//
//
//        Resp response = null;
//
//        List<Resp> Responses = new ArrayList<>();
//
//        try {
//
//            response = new Resp();
//            con = Utilities.getConnection();
//            con = con.unwrap(OracleConnection.class);
//            cll = (OracleCallableStatement) con.prepareCall("{?=call esb_fcc_init_txn(?,?,?,?,?,?,?,?,?,?,?,?)}");
//            int count = 0;
//
//            cll.registerOutParameter(++count, OracleTypes.INTEGER);
//            cll.setString(++count, record.getP_reference2());
//            cll.setString(++count, record.getP_event());
//            cll.setString(++count, record.getP_platformid());
//            cll.setString(++count, record.getP_clientip());
//            cll.setString(++count, record.getP_branch());
//            cll.setString(++count, record.getP_debit_acct());
//            cll.setString(++count, record.getP_debit_amount());
//            cll.setString(++count, record.getP_crdrflag());
//            cll.registerOutParameter(++count, Types.DATE);
//            cll.registerOutParameter(++count, OracleTypes.VARCHAR);
//            cll.registerOutParameter(++count, OracleTypes.VARCHAR);
//            cll.registerOutParameter(++count, OracleTypes.VARCHAR);
//
//            System.out.println("it get here .... ");
//
//            cll.execute();
//
//            System.out.println("it get here 2.... ");
//
//            Output = (Integer) cll.getObject(1);
//            Output1 = (String) cll.getObject(11);
//            Output2 = (String) cll.getObject(12);
//            Output3 = (String) cll.getObject(13);
//
//            System.out.println("Output " + Output);
//            System.out.println("Output1 1: " + Output1);
//            System.out.println("Output 2: " + Output2);
//            System.out.println("Output 3: " + Output3);
//
//            if (Output.equals(0)) {
//
//                System.out.println("it get here 3.... ");
//
//                response.setResponsecode((String) cll.getObject(12));
//                response.setResponseMessage((String) cll.getObject(13));
//
//                Responses.add(response);
//
//            } else {
//                response.setResponsecode((String) cll.getObject(12));
//                response.setResponseMessage((String) cll.getObject(13));
//
//                Responses.add(response);
//
//            }
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (con != null) {
//                    con.close();
//                }
//                if (rs != null) {
//                    rs.close();
//                }
//                if (cll != null) {
//                    cll.close();
//                }
//            } catch (Exception e1) {
//                e1.printStackTrace();
//            }
//        }
////            return  ResponseEntity.status(HttpStatus.OK).body(Responses);
//        return Output;
//    }
//
//
//    @PostMapping(value = "/Addrecord", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<?> AddRecord(@RequestBody AddParam addParam) {
//        Connection conn = null;
//        ResultSet Result = null;
//        OracleCallableStatement stmt = null;
//        OracleCallableStatement cll = null;
//
//        Integer resultout = null;
//        Integer resultout1 = null;
//        Integer resultout2 = null;
//        Integer resultoutFinal = null;
//        String Output2 = null;
//        String Output3 = null;
//        String Output4 = null;
//        String Output5 = null;
//        String Output6 = null;
//        String genbatch = null;
//        Resp response = new Resp();
//
//        List<Resp> Responses = new ArrayList<>();
//
//        System.out.println("i'm here 1");
//        System.out.println("addParam: " + addParam.toString());
//
//        try {
//            conn = Utilities.getConnection();
//            conn = conn.unwrap(OracleConnection.class);
//
//            System.out.println("##############################");
//            System.out.println("About to initiate transaction");
//            System.out.println("##############################");
//
//            stmt = (OracleCallableStatement) conn.prepareCall("{?=call esb_fcc_init_txn(?,?,?,?,?,?,?,?,?,?,?,?)}");
//            int counts = 0;
//
//            stmt.registerOutParameter(++counts, OracleTypes.INTEGER);
//            stmt.setString(++counts, addParam.getReference());
//            stmt.setString(++counts, addParam.getEvent());
//            stmt.setString(++counts, addParam.getPlatformid());
//            stmt.setString(++counts, addParam.getClientIp());
//            stmt.setString(++counts, addParam.getBranch());
//            stmt.setString(++counts, addParam.getDebitField().getAccountnum());
//            stmt.setInt(++counts, addParam.getDebitField().getTxnamount());
//            stmt.setString(++counts, addParam.getDebitField().getCrdrflag());
//            stmt.registerOutParameter(++counts, Types.DATE);
//            stmt.registerOutParameter(++counts, OracleTypes.VARCHAR);
//            stmt.registerOutParameter(++counts, OracleTypes.VARCHAR);
//            stmt.registerOutParameter(++counts, OracleTypes.VARCHAR);
//
//
//            stmt.execute();
//
//
//            resultout = (Integer) stmt.getObject(1);
//
//            Output2 = (String) stmt.getObject(12);
//            Output3 = (String) stmt.getObject(13);
//            genbatch = (String) stmt.getObject(11);
//
//            System.out.println("===========genbatch++++++++++" + genbatch);
//
//            System.out.println("Output " + resultout);
//            System.out.println("Intiate : " + Output3);
//
//            response.setResponsecode(Output2);
//            response.setResponseMessage(Output3);
//
////            Responses.add(response);
//
//            if (resultout == 0) {
//
//                System.out.println("##############################");
//                System.out.println("About to add Record 1");
//                System.out.println("##############################");
//
//                stmt = (OracleCallableStatement) conn.prepareCall("{?=call esb_fcc_instr_add_record(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
//                int cnt = 0;
//
//                stmt.registerOutParameter(++cnt, OracleTypes.INTEGER);
//                stmt.setString(++cnt, addParam.getPlatformid());
//                stmt.setString(++cnt, addParam.getBranch());
////                stmt.setString(++cnt, addParam.getBatchno());
//                stmt.setString(++cnt, genbatch);
//                stmt.setString(++cnt, addParam.getReference());
//                stmt.setString(++cnt, addParam.getDebitField().getAccountnum());
//                stmt.setString(++cnt, addParam.getDebitField().getAccountbranch());
//                stmt.setString(++cnt, addParam.getDebitField().getCrdrflag());
//                stmt.setInt(++cnt, addParam.getDebitField().getTxnamount());
//                stmt.setString(++cnt, addParam.getDebitField().getAmttype());
//                stmt.setString(++cnt, addParam.getDebitField().getTxncurrency());
//                stmt.setDate(++cnt, addParam.getInitdate());
//                stmt.setDate(++cnt, addParam.getValuedate());
//                stmt.setInt(++cnt, addParam.getDebitField().getSeqno());
//                stmt.setString(++cnt, addParam.getDebitField().getTxndesc());
//                stmt.setInt(++cnt, addParam.getDebitField().getExrate());
//                stmt.setString(++cnt, addParam.getDebitField().getTrantype());
//                stmt.setString(++cnt, addParam.getDebitField().getInstrumentno());
//                stmt.registerOutParameter(++cnt, OracleTypes.VARCHAR);
//                stmt.registerOutParameter(++cnt, OracleTypes.VARCHAR);
//
//
//                stmt.execute();
//
//                resultout = (Integer) stmt.getObject(1);
//
//                System.out.println("resultout: " + resultout);
//                System.out.println("Output3 1: " + Output4);
//
//                Output2 = (String) stmt.getObject(19);
//                Output3 = (String) stmt.getObject(20);
//
//                response.setResponsecode(Output2);
//                response.setResponseMessage(Output3);
//
//
////                Responses.add(response);
//
//                if (resultout == 0) {
//
//                    System.out.println("##############################");
//                    System.out.println("About to add Record 2");
//                    System.out.println("##############################");
//
//                    cll = (OracleCallableStatement) conn.prepareCall("{?=call esb_fcc_instr_add_record(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
//                    int cnt1 = 0;
//
//                    System.out.println("##############################");
//                    System.out.println("Came after first add record============= 2");
//                    System.out.println("##############################");
//
//                    cll.registerOutParameter(++cnt1, OracleTypes.INTEGER);
//                    cll.setString(++cnt1, addParam.getPlatformid());
//                    cll.setString(++cnt1, addParam.getBranch());
////                    cll.setString(++cnt1, addParam.getBatchno());
//                    cll.setString(++cnt1, genbatch);
//                    cll.setString(++cnt1, addParam.getReference());
//                    cll.setString(++cnt1, addParam.getCreditField().getAccountnum());
//                    cll.setString(++cnt1, addParam.getCreditField().getAccountbranch());
//                    cll.setString(++cnt1, addParam.getCreditField().getCrdrflag());
//                    cll.setInt(++cnt1, addParam.getCreditField().getTxnamount());
//                    cll.setString(++cnt1, addParam.getCreditField().getAmttype());
//                    cll.setString(++cnt1, addParam.getCreditField().getTxncurrency());
//                    cll.setDate(++cnt1, addParam.getInitdate());
//                    cll.setDate(++cnt1, addParam.getValuedate());
//                    cll.setInt(++cnt1, addParam.getCreditField().getSeqno());
//                    cll.setString(++cnt1, addParam.getCreditField().getTxndesc());
//                    cll.setInt(++cnt1, addParam.getCreditField().getExrate());
//                    cll.setString(++cnt1, addParam.getCreditField().getTrantype());
//                    cll.setString(++cnt1, addParam.getCreditField().getInstrumentno());
//                    cll.registerOutParameter(++cnt1, OracleTypes.VARCHAR);
//                    cll.registerOutParameter(++cnt1, OracleTypes.VARCHAR);
//
//                    cll.execute();
//
//                    System.out.println("##############################");
//                    System.out.println("Came after first add record=============3");
//                    System.out.println("##############################");
//
//                    resultout = (Integer) cll.getObject(1);
//                    Output3 = (String) cll.getObject(20);
//
//                    System.out.println("Output3 1: " + Output3);
//                    System.out.println("resultout 1: " + resultout);
//
//
//                    Output2 = (String) stmt.getObject(19);
//                    Output3 = (String) stmt.getObject(20);
//
//                    response.setResponsecode(Output2);
//                    response.setResponseMessage(Output3);
//
////                    Responses.add(response);
//
//                    if (resultout == 0) {
////                        cll = (OracleCallableStatement) conn.prepareCall("{?=call esb_fcc_post_txn(?,?,?,?,?,?)}");
////                        cll.registerOutParameter(1,OracleTypes.INTEGER);
////                        cll.setString(2,addParam.getReference());
////                        cll.setString(3,addParam.getPlatformid());
////                        cll.setString(4,addParam.getBranch());
////                        cll.setString(5,addParam.getBatchno());
////                        cll.registerOutParameter(6,OracleTypes.VARCHAR);
////                        cll.registerOutParameter(7,OracleTypes.VARCHAR);
//
//                        cll = (OracleCallableStatement) conn.prepareCall("{?=call esb_fcc_post_txn(?,?,?,?,?,?)}");
//                        cll.registerOutParameter(1, OracleTypes.INTEGER);
//                        cll.setString(2, addParam.getReference());
//                        cll.setString(3, addParam.getPlatformid());
//                        cll.setString(4, addParam.getBranch());
//                        cll.setString(5, genbatch);
//                        cll.registerOutParameter(6, OracleTypes.VARCHAR);
//                        cll.registerOutParameter(7, OracleTypes.VARCHAR);
//
//                        System.out.println("================================genbatch");
//                        System.out.println("About to post to flexcube");
//                        System.out.println("=================================");
//
//                        cll.execute();
//
//                        resultout = (Integer) cll.getObject(1);
//                        Output5 = (String) cll.getObject(6);
//                        Output6 = (String) cll.getObject(7);
//
//                        System.out.println("resultoutFinal: " + resultout);
//                        System.out.println("Output5 : " + Output5 + " Output6: " + Output6);
//
//                        if (resultout == 0) {
//
//                            Output5 = (String) cll.getObject(6);
//                            Output6 = (String) cll.getObject(7);
//
//                            response.setResponsecode(Output5);
//                            response.setResponseMessage(Output6);
//
//                        } else {
//
//                            Output5 = (String) cll.getObject(6);
//                            Output6 = (String) cll.getObject(7);
//
//                            response.setResponsecode(Output5);
//                            response.setResponseMessage(Output6);
//                        }
//
//
////                        Responses.add(response);
//
//                    } else {
//
//                        System.out.println("##############################");
//                        System.out.println("Came here for exception failed to post flexcube");
//                        System.out.println("##############################");
//
//                        System.out.println("Output3 1: " + Output3);
//                        System.out.println("resultout 1: " + resultout);
//
//
//                        Output2 = (String) cll.getObject(6);
//                        Output3 = (String) cll.getObject(7);
//
//                        response.setResponsecode(Output2);
//                        response.setResponseMessage(Output3);
//
//                    }
//
//                } else {
//
//                    System.out.println("##############################");
//                    System.out.println("Came here for exception 2");
//                    System.out.println("##############################");
//
//                    System.out.println("Output3 1: " + Output3);
//                    System.out.println("resultout 1: " + resultout);
//
//
//                    Output2 = (String) cll.getObject(19);
//                    Output3 = (String) cll.getObject(20);
//
//                    response.setResponsecode(Output2);
//                    response.setResponseMessage(Output3);
//
//                }
//            } else {
//                System.out.println("##############################");
//                System.out.println("Came here for exception");
//                System.out.println("##############################");
//
//                Output2 = (String) stmt.getObject(12);
//                Output3 = (String) stmt.getObject(13);
//
//                System.out.println("Output " + resultout);
//                System.out.println("Intiate : " + Output3);
//
//                response.setResponsecode(Output2);
//                response.setResponseMessage(Output3);
//            }
//
//            System.out.println("Came after Post final destination =========");
//
//        } catch (Exception e) {
//            e.printStackTrace();
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
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(response);
//    }
}
