package hello.capstone.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import hello.capstone.service.OAuthService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/kakao")
public class OAuthController {

	private final OAuthService oauthService;
	
    @ResponseBody
    @GetMapping("/oauth")
    public void kakaoCallback(@RequestParam String code) {
        log.info("code={}",code);
        
        String access_Token = oauthService.getKakaoAccessToken(code);

        
        log.info("acttn = {}", access_Token);
        
    }
}









