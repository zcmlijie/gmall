package message;

import org.apache.http.HttpEntity;
import org.apache.http.entity.InputStreamEntity;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Util {
  /**
   * 获取请求Content-Type: application/json 类型的二进制数据，转换为JSON数据格式
   *
   * @param request 客户端请求
   * @return JSON字符串
   * @author Mbox
   */
  public static String toJSON(HttpServletRequest request) {
    InputStreamReader input = null;
    BufferedReader reader = null;
    try {
      HttpEntity data = new InputStreamEntity(request.getInputStream(), request.getContentLength());
      input = new InputStreamReader(data.getContent(), "utf-8");
      reader = new BufferedReader(input);
      String line = null;
      StringBuilder sb = new StringBuilder();
      while ((line = reader.readLine()) != null) {
        sb.append(line);
      }

      return sb.toString();

    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        input.close();
        reader.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    return null;
  }
}
