package hello.capstone.controller;

import java.util.HashMap;
import java.util.Objects;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import hello.capstone.dto.Member;
import hello.capstone.service.LoginService;
import hello.capstone.service.OAuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@ResponseBody
@AllArgsConstructor
public class OAuthController {

	private final OAuthService oauthService;
	private final LoginService loginService;
	/*
	 * 카카오 로그인 인가토큰 받고, 회원 정보 받아오기
	 */
    @ResponseBody
    @GetMapping("/kakao/oauth")
    public HashMap<String, Object> getKakaoTokenAndInfo(@RequestParam String code) {
        log.info("code={}",code);
        
        String accessToken = oauthService.getKakaoAccessToken(code);
        HashMap<String, Object> infos = oauthService.getUserInfo(accessToken);
        
        for(String infoKey : infos.keySet()){	
        	log.info("info = {}, {}",infoKey, infos.get(infoKey));
        	
        }
        log.info("acttn = {}", accessToken);  
        
        HashMap<String, Object> token = new HashMap<>();
        token.put("url", "/kakao/oauth/signUp");
        token.put("infos", infos);
             
        
        return token;
    }
    
    
    /*
     * 받은 정보로 비회원이라면 회원가입
     */
    @PostMapping("/kakao/oauth/signUp")
    public String kakaoSignUp(@RequestBody Member member , HttpServletRequest request) {
    	log.info("name = {}", member.getName());
    	log.info("id = {}", member.getId());
    	
    	if(loginService.kakaoSignUp(member) == false) {
     		//이미 가입되어 있는 멤버 -> 바로 로그인
    		HttpSession session = request.getSession();
    		session.setAttribute("id", member.getId());
    		session.setAttribute("name", member.getName());
    		session.setAttribute("nickname", member.getNickname());
    		
    		return "/home_user";
    	}
    	 	
    	return "/login";
    	
    }
    
    /*
     * 네이버
     */
    
    @GetMapping("/login/oauth2/Naver_loading2")
    public String naverOAuthRedirect(@RequestParam String code, @RequestParam String state) {
        // RestTemplate 인스턴스 생성
        RestTemplate rt = new RestTemplate();

        HttpHeaders accessTokenHeaders = new HttpHeaders();
        accessTokenHeaders.add("Content-type", "application/x-www-form-urlencoded");

        MultiValueMap<String, String> accessTokenParams = new LinkedMultiValueMap<>();
        accessTokenParams.add("grant_type", "authorization_code");
        accessTokenParams.add("client_id", "rmptq4twehWBueMreZ2L");
        accessTokenParams.add("client_secret", "Vr7_Qu_Nhs");
        accessTokenParams.add("code" , code);   // 응답으로 받은 코드
        accessTokenParams.add("state" , state); // 응답으로 받은 상태

        HttpEntity<MultiValueMap<String, String>> accessTokenRequest = new HttpEntity<>(accessTokenParams, accessTokenHeaders);

        ResponseEntity<String> accessTokenResponse = rt.exchange(
                "https://nid.naver.com/oauth2.0/token",
                HttpMethod.POST,
                accessTokenRequest,
                String.class
        );
        
        log.info("accessTokenResponse.getBody() ={} ",accessTokenResponse.getBody());
        return "accessToken: " + accessTokenResponse.getBody();
    }
    
    
    
}









