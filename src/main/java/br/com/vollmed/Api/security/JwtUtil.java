package br.com.vollmed.Api.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

/*

 Assinar com a nossa chave secreta
 Validar os dados recebidos

 Um token JWT tem 3 partes por ponto:
 HEADER.PAYLOAD.SIGNATURE
 1. HEADER -> Tipo do token e o algoritmo da requisição.
 2. PAYLOAD -> dados (claims) - aqui guardamos os dados do usuário, como nome e ROLE.
 3. SIGNATURE -> assinatura gerada com a chave secreta.

 */

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

    // Método que verifica/extrai os dados do payload (segunda parte do token)
    private Claims extrairClaims(String token) {
        // Informa a chave secreta para que em seguida o método parser consiga verificar a assinatura vigente
        // É a mesma usada para gerarToken() - só quem tem a jwssecret(assinatura) consegue validar
        return Jwts.parser()
            .verifyWith(getChave())
            .build() // Construindo o parser com as configurações acima
            .parseSignedClaims(token) // Faz o parse do token: Decodificar a chave e verifica se a assinatura é válida e retorna a resposta
            // Ele lança uma exceção se: 1. o token está mal formatado, inválido ou expirado.
            .getPayload(); // Retorna apenas o payLoad(Claims) que é a parte com os dados do usuário. Descarta o header e a assinatura, que já cumpriram o papel na verificação
    }

    // Método que lê e extrai o dado do username recebido na requisição
    private String extrairUsername(String token){
        // getSubject() -> retorna o campo "sub" é onde gravamos o gerarToken()
        return extrairClaims(token).getSubject();

    }
    
}
