package cn.edu.ncepu.researchplatform.utils;

import cn.edu.ncepu.researchplatform.security.PeopleDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class JWTUtil {
    //引入 <groupId>io.jsonwebtoken</groupId>
    //            <artifactId>jjwt</artifactId>
    private static final String SECRET = "nerlngkerngl56666666666666";
    private static final long EXPIRE_TIME = 1000 * 60 * 60 * 24 * 15; //毫秒
    private static final String ISSUER = "软件提供商";

    //Subject 用户唯一ID  Audience 可重复的昵称或姓名 ExpireTime 过期时间   Claims  自定义的负载，非官方提供，必须先设置
    public static String createjwt(Authentication details) {
        Map<String, Object> claims = new HashMap<>();
        //自定义的负载部分
        claims.put("auth", details.getAuthorities());
        return Jwts.builder().setClaims(claims).setIssuer(ISSUER)
                .setAudience(null).setSubject(details.getName())
                .setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + EXPIRE_TIME))
                .setId(UUID.randomUUID().toString()).signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }

    public static Claims getJwt(String jwt) {
        JwtParser jwtParser = Jwts.parser();
        return jwtParser.setSigningKey(SECRET).parseClaimsJws(jwt).getBody();
    }

    public static String parseAuthorization() {
        String authorization = Utils.getRequest().getHeader("Authorization");
        if (!StringUtils.hasText(authorization)) {
            throw new RuntimeException();
        }
        try {
            return getJwt(authorization.split(" ")[1]).getSubject();
        } catch (Exception e) {
            //校验Bearer Token
            return getJwt(authorization).getSubject();
        }

    }
}
