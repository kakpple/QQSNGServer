package nanoFuntas.qqsngServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 * Servlet implementation class QQSNGServlet
 */
@WebServlet("/QQSNGServlet")
public class QQSNGServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private boolean DEBUG = true;
	private String TAG = "QQSNGServlet";
    /**
     * Default constructor. 
     */
    public QQSNGServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();	
		out.write("1");
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(DEBUG) System.out.println(TAG + ": doPost");						
		
		String type = null;
		
		JSONObject mJsonReq = getJsonReq(request);
		String mReqType = (String) mJsonReq.get("REQ_TYPE");
		if(DEBUG) System.out.println(TAG + ", " + mReqType);	
		
		if(mReqType.equals("FETCH_SELF_INFO")){
			JSONObject jsonRsp = new JSONObject();
			jsonRsp.put("RSP_TYPE", "RSP_TYPE_FETCH_SELF_INFO");
			
			StringWriter sw = new StringWriter();
			jsonRsp.writeJSONString(sw);
			String s = sw.toString();
			
			String ss = jsonRsp.toString();
			
			sendStrRsp(response, ss);
		}
			
		
		/*
		try {
			type = jsonReq.getString("REQ_TYPE");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		/*
		if(type.equals("FETCH_SELF_INFO")){
			JSONObject jsonRsp = new JSONObject();
			try {
				jsonRsp.put("RSP_TYPE", "RSP_TYPE_FETCH_SELF_INFO");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
			sendJsonRsp(response, jsonRsp);
		}
		*/
		
		/*
		String type = request.getParameter("REQ_TYPE");	
		DatabaseService mDatabaseService = new DatabaseService();		
		
		if(type.equals("FETCH_SELF_INFO")){
			String selfID = request.getParameter("SELF_ID");
			if( mDatabaseService.isUserRegistered(selfID) ){
				if(DEBUG) System.out.println(TAG + ": isUserRegistered == true");
				out.print(1);
			} else {
				if(DEBUG) System.out.println(TAG + ": isUserRegistered == false");
				mDatabaseService.registerUser(selfID);
				out.print(5);
			};			
		}
		*/
	}
	
	private void sendStrRsp(HttpServletResponse response, String strToSend) {
		OutputStream outStrm = null;
		ObjectOutputStream objOutStrm = null;
		
		// write date to be sent to client
		try{
			outStrm = response.getOutputStream();
			objOutStrm = new ObjectOutputStream(outStrm);
			objOutStrm.writeObject(strToSend);
			objOutStrm.flush();
		} catch (IOException e){
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		} finally{
			// release resources
			try{
				if(outStrm != null) outStrm.close();
				if(objOutStrm != null) objOutStrm.close();
			} catch (Exception e){
				e.printStackTrace();
			}
		}
	}

	/*
	private void sendJsonRsp(HttpServletResponse response, JSONObject jsonToSend) {
		String jsonStr = null;
		jsonStr = jsonToSend.toString();
		sendStrRsp(response, jsonStr);
	}
	*/
	
	private String getStrReq(HttpServletRequest request) {
		String strReq = null;		
		InputStream inStrm = null;
		ObjectInputStream objInStrm = null;
		
		// get date from client
		try {
			inStrm = request.getInputStream();
			objInStrm = new ObjectInputStream(inStrm);
			strReq = (String) objInStrm.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e){			
			e.printStackTrace();
		} finally {
			// release resources
			try{
				if(inStrm != null) inStrm.close();
				if(objInStrm != null) objInStrm.close();
			} catch(Exception e){
				e.printStackTrace();
			}
		}

		return strReq;
	}
	
	
	private JSONObject getJsonReq(HttpServletRequest request){
		String strReq = null;
		JSONObject jsonReq = null;
		strReq = getStrReq(request);
		jsonReq = (JSONObject) JSONValue.parse(strReq);
		
		return jsonReq;
	}
}
