package com.example.nzlive.util;

        import android.content.Context;
        import android.util.Log;

        import com.android.volley.Request;
        import com.android.volley.RequestQueue;
        import com.android.volley.Response;
        import com.android.volley.VolleyError;
        import com.android.volley.toolbox.JsonObjectRequest;
        import com.android.volley.toolbox.Volley;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;
        import org.json.JSONTokener;

        import java.io.BufferedReader;
        import java.io.FileNotFoundException;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.io.OutputStream;
        import java.io.OutputStreamWriter;
        import java.io.Writer;
        import java.util.ArrayList;

public class GETHttp {
    JSONObject jsonObject=new JSONObject();

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }


    public void setResponse(String strUrl, final Context context){

        RequestQueue mQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, strUrl, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // TODO Auto-generated method stu
//                Log.d("AAA", "onResponse: "+response.toString());
                Writer writer = null;
                OutputStream out = null;
                try {
                    out = context.openFileOutput("weather", Context.MODE_PRIVATE);
                    writer = new OutputStreamWriter(out);
                    writer.write(response.toString());
                }catch (Exception e) {

                }finally {
                    if (writer != null) {
                        try {
                            writer.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("AAA", "onErrorResponse: "+volleyError.toString());
            }
        });
        mQueue.add(jsonObjectRequest);
    }
    public JSONObject getResponse(Context context){
        ArrayList crimes = new ArrayList();
        BufferedReader reader = null;
        try {
            InputStream in = context.openFileInput("weather");
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder jsonString = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                jsonString.append(line);
            }
            //JSONArray array = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();
//            Log.d("AAA", "getResponse: "+jsonString.toString());
            JSONObject jsonObject=new JSONObject(jsonString.toString());
            setJsonObject(jsonObject);
//            for (int i = 0; i < array.length(); i++) {
//                crimes.add(new Crime(array.getJSONObject(i)));
//            }
        } catch (Exception e) {

        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return getJsonObject();
    }
}
