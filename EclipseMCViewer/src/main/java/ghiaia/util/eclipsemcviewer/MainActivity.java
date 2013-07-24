package ghiaia.util.eclipsemcviewer;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

public class MainActivity extends Activity {
    public static final String PREFS_NAME = "SAVED";
    PromptDialog dlg;
    WebView theview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        theview = (WebView) findViewById(R.id.webview);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        final String[] apikey = {settings.getString("APIKEY", "0")};
        if(apikey[0] =="0"){     dlg = new PromptDialog(this, R.string.dtitle, R.string.dmsg) {
            @Override
            public boolean onOkClicked(String input) {
                if(input.length()>24){
                    apikey[0] =input;
                    SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("APIKEY", input);
                    editor.commit();
                    theview.loadUrl("https://eclipsemc.com/mw.php?key="+input);
                    theview.reload();
                    return true; }
                else{         Toast.makeText(this.getContext(), "Please insert valid API Key", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        };
            dlg.show();

        }else{
        theview.loadUrl("https://eclipsemc.com/mw.php?key="+apikey[0]);
        theview.reload();
        }
        WebSettings webSettings = theview.getSettings();
        webSettings.setJavaScriptEnabled(true);




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_settings:
                dlg = new PromptDialog(this, R.string.dtitle, R.string.dmsg) {
                    @Override
                    public boolean onOkClicked(String input) {
                        if(input.length()>24){
                            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putString("APIKEY", input);
                            editor.commit();
                            theview.loadUrl("https://eclipsemc.com/mw.php?key="+input);
                            theview.reload();

                            return true; }
                        else{         Toast.makeText(this.getContext(), "Please insert valid API Key", Toast.LENGTH_SHORT).show();
                            return false;
                        }
                    }
                };
                dlg.show();

                return true;
            case R.id.exit:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    
}
