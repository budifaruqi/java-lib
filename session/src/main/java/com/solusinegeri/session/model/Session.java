/*    */
package com.solusinegeri.session.model;
/*    */
/*    */

import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/*    */
/*    */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Hidden
public class Session {

  private Map<String, Object> data;

  private String id;

  private String userId;
}