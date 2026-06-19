package br.com.vollmed.Api.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
    // Criar o token com dados do usuario
    @Value("${jwt.secret}") //jwt.secret é o padrão usado
    private String secret;

    // Token duração de 24h -> millissegundos
    private static final long EXPIRACAO_MS = 1000L * 60 * 60 * 24;

    // Converter a String "secret" em um objeto SecretKey criptografado
    // HMAC - SHA256 => hmacShaKeyFor()
    private SecretKey getChave(){
        return Keys.hmacShaKeyFor(secret.getBytes()); //getBytes pega os caracteres que estão lá dentro
    }

    // Gerar Token (montagem em camadas jwt)
    public String gerarToken(String username, String role){
        return Jwts.builder() // builder significa que tá construindo alguma coisa
            .subject(username) //dono do token (lembrando que pode ser cpf, email, nome e etc)
            .claim("role", role)// dado extra, o que pode acessar ou não dentro do app (dentro do payload)
            .issuedAt(new Date()) //iAt -> vai registrar o momento exato que o token foi criado (saber quando o usuário fez login)
            .expiration(new Date(System.currentTimeMillis() + EXPIRACAO_MS))
            .signWith(getChave())
            .compact(); //HEADER.PAYLOAD.SIGNATURE
    }

    // Validar se o token realmente existe/é válido
    public boolean isTokenValido(String token, String username){
        // Extrair o username de dentro do token (campo "sub" do payload)
        String usernameDoToken = extrairUsername(token);

        // Condição 1-> o username do token deve ser igual ao username esperado.
        // Impede que um token de outro usuário seja usado no lugar. 

        //Condição 2 -> a data de expiração ainda não passou. Ambas as afirmações && precisam ser verdadeiras
        return usernameDoToken.equals(username) && !isExpirado(token);
    }

    // Método que verifica se a data de expiração registrada no token já passou
    private boolean isExpirado(String token){
        return extrairClaims(token).getExpiration().before(new Date());
    }

    // Assinar com a nossa chave secreta
    // Validar os dados recebidos

    // Um token JWT tem 3 partes por ponto:
    // HEADER.PAYLOAD.SIGNATURE
   // 1. HEADER -> Tipo do token e o algoritmo da requisição.
   // 2. PAYLOAD -> dados (claims) - aqui guardamos os dados do usuário, como nome e ROLE.
   // 3. SIGNATURE -> assinatura gerada com a chave secreta.
    
}
