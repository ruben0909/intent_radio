package org.smblott.intentradio;

import android.os.AsyncTask;
import java.lang.Thread;

public abstract class Later extends AsyncTask<Integer, Void, Void>
{
   private static final int default_seconds = 120;
   private static Later other = null;

   private int seconds = default_seconds;
   private int then;

   /* ********************************************************************
    * Constructors...
    */

   Later(int secs)
   {
      if ( other != null )
         other.cancel(true);
      other = this;

      seconds = 0 < secs ? secs : default_seconds;
      then = Counter.now();
   }

   Later()
      { this(default_seconds); }

   /* ********************************************************************
    * Background job...
    */

   protected Void doInBackground(Integer... args)
   {
      try { Thread.sleep(seconds * 1000); }
      catch ( Exception e ) { }
      return null;
   }

   // This runs on the main thread...
   //
   protected void onPostExecute(Void ignored)
   {
      if ( ! isCancelled() && Counter.still(then) )
         later();
   }

   public abstract void later();
}
