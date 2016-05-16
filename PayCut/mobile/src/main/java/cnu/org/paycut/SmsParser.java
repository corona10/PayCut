package cnu.org.paycut;

import android.telephony.SmsMessage;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * Created by corona10 on 2016. 5. 15..
 */
public class SmsParser {

  private SmsParser()
  {

  }
  static SmsLog parse(SmsMessage message)
  {
    if(message.getOriginatingAddress().equals("15991155"))
    {
      // 하나 SK카드
      /**
       * 하나SK체크카드에서 온 메시지를 파싱한다.
       * 다음과 같은 메시지 포맷을 파싱할 수 있다.
       *
       * <pre>
       * 하나SK체크카드(3*3*)이*행님 03/20 10:16 일시불/ 2,000원/승인/ 브레댄코 역삼점
       * </pre>
       */
      SmsLog log = new SmsLog();
      StringTokenizer tokenizer = new StringTokenizer(message.getMessageBody(), "/");
      tokenizer.nextToken();
      tokenizer.nextToken();
      String price = tokenizer.nextToken();
      price = price.trim();
      price = price.replace(",", "");
      price = price.replace("원", "");
      String store = tokenizer.nextToken();
      store = store.trim();

      SimpleDateFormat formatter = new SimpleDateFormat ( "yyyy.MM.dd HH:mm:ss", Locale.KOREA );
      String dTime = formatter.format(message.getTimestampMillis());
      Date currentTime = null;
      try {
        currentTime = formatter.parse(dTime);
      } catch (ParseException e) {
        e.printStackTrace();
        return null;
      }
      log.setDate(currentTime);
      log.setPrice(Integer.parseInt(price));
      log.setStore(store);

      Log.d("sms info:", dTime);
      Log.d("sms info:", price );
      Log.d("sms info:", store );
      log.setIsSent(false);
      return log;
    }else if(message.getOriginatingAddress().equals("01033539127")){
      Log.d("sms", "test");
      SmsLog log = new SmsLog();
      StringTokenizer tokenizer = new StringTokenizer(message.getMessageBody(), "\n");
      tokenizer.nextToken();
      tokenizer.nextToken();
      String price = tokenizer.nextToken();
      price = price.trim();
      price = price.replace(",", "");
      price = price.replace("원", "");
      String store = tokenizer.nextToken();
      store = store.replace("사용", "");
      store = store.trim();

      SimpleDateFormat formatter = new SimpleDateFormat ( "yyyy.MM.dd HH:mm:ss", Locale.KOREA );
      String dTime = formatter.format(message.getTimestampMillis());
      Date currentTime = null;
      try {
        currentTime = formatter.parse(dTime);
      } catch (ParseException e) {
        e.printStackTrace();
        return null;
      }

      log.setDate(currentTime);
      log.setPrice(Integer.parseInt(price));
      log.setStore(store);

      Log.d("sms info:", dTime);
      Log.d("sms info:", price );
      Log.d("sms info:", store );
      log.setIsSent(false);
      return log;

    }else
    {
      return null;
    }
  }


}
