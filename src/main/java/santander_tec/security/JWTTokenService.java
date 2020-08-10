package santander_tec.security;

import com.google.common.collect.ImmutableMap;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.compression.GzipCompressionCodec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Map;
import java.util.function.Supplier;

import static io.jsonwebtoken.SignatureAlgorithm.HS256;
import static io.jsonwebtoken.impl.TextCodec.BASE64;
import static java.util.Objects.requireNonNull;
import static org.apache.commons.lang3.StringUtils.substringBeforeLast;

@Service
public class JWTTokenService implements Clock {

  private static final String DOT = ".";
  private static final GzipCompressionCodec COMPRESSION_CODEC = new GzipCompressionCodec();
  private final String issuer;
  private final int expirationSec;
  private final int clockSkewSec;
  private final String secretKey;

  JWTTokenService(@Value("${jwt.issuer:octoperf}") final String issuer,
                  @Value("${jwt.expiration-sec:86400}") final int expirationSec,
                  @Value("${jwt.clock-skew-sec:300}") final int clockSkewSec,
                  @Value("${jwt.secret:secret}") final String secret) {
    super();
    this.issuer = requireNonNull(issuer);
    this.expirationSec = expirationSec;
    this.clockSkewSec = clockSkewSec;
    this.secretKey = BASE64.encode(requireNonNull(secret));
  }

  public String permanent(final Map<String, String> attributes) {
    return newToken(attributes, 0);
  }

  public String expiring(final Map<String, String> attributes) {
    return newToken(attributes, expirationSec);
  }

  private String newToken(final Map<String, String> attributes, final int expiresInSec) {
    LocalDateTime now = LocalDateTime.now();
    Claims claims = Jwts.claims().setIssuer(issuer).setIssuedAt(Date.from(now.toInstant(ZoneOffset.UTC)));

    if (expiresInSec > 0) {
      LocalDateTime expiresAt = now.plusSeconds(expiresInSec);
      claims.setExpiration(Date.from(expiresAt.toInstant(ZoneOffset.UTC)));
    }
    claims.putAll(attributes);

    return Jwts.builder().setClaims(claims).signWith(HS256, secretKey)
            .compressWith(COMPRESSION_CODEC).compact();
  }

  public Map<String, String> verify(final String token) {
    final JwtParser parser = Jwts.parser().requireIssuer(issuer).setClock(this)
      .setAllowedClockSkewSeconds(clockSkewSec).setSigningKey(secretKey);
    return parseClaims(() -> parser.parseClaimsJws(token).getBody());
  }

  public Map<String, String> untrusted(final String token) {
    final JwtParser parser = Jwts.parser().requireIssuer(issuer)
      .setClock(this).setAllowedClockSkewSeconds(clockSkewSec);

    // See: https://github.com/jwtk/jjwt/issues/135
    final String withoutSignature = substringBeforeLast(token, DOT) + DOT;
    return parseClaims(() -> parser.parseClaimsJwt(withoutSignature).getBody());
  }

  private static Map<String, String> parseClaims(final Supplier<Claims> toClaims) {
    try {
      final Claims claims = toClaims.get();
      final ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();
      for (final Map.Entry<String, Object> e: claims.entrySet()) {
        builder.put(e.getKey(), String.valueOf(e.getValue()));
      }
      return builder.build();
    } catch (final IllegalArgumentException | JwtException e) {
      return ImmutableMap.of();
    }
  }

  @Override
  public Date now() {
    return Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC));
  }

}