package hello.capstone.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import hello.capstone.service.OAuthService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@ResponseBody
@AllArgsConstructor
public class OAuthController {

	private final OAuthService oauthService;
	/*
	 * 카카오 로그인 인가토큰 받기
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
        token.put("url", "/kakao/oauth/login");
        token.put("infos", infos);
        
        return token;
    }
    
    @GetMapping("/kakao/oauth/login")
    public void getKakaoInfo(@RequestParam String name,@RequestParam String id  ) {
    	log.info("name = {}", name);
    	log.info("id = {}", id);
    }
    
    
}









