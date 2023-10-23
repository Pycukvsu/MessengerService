package com.relex.profile.controllers;

import com.relex.profile.dtos.PasswordDto;
import com.relex.profile.dtos.UpdateProfileDto;
import com.relex.profile.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequiredArgsConstructor
@Slf4j
public class ProfileControllers {

    private final ProfileService profileService;

    @PostMapping("/user/profile/update")
    public ResponseEntity<?> updateProfile(@RequestBody UpdateProfileDto updateProfileDto){
        return profileService.updateProfile(updateProfileDto);
    }

    @PostMapping("/user/profile/freeze")
    public ResponseEntity<?> freezeProfileActivity(){
        return profileService.freezeProfileActivity();
    }

    @PostMapping("/user/profile/unfreeze")
    public ResponseEntity<?> unfreezeTheProfile(){
        return profileService.unfreezeTheProfile();
    }

    @PostMapping("/user/profile/delete")
    public ResponseEntity<?> deleteProfile(){
        return profileService.deleteProfile();
    }

    @PostMapping("/user/profile/password/update")
    public ResponseEntity<?> updatePassword(@RequestBody PasswordDto passwordDto){
        return profileService.updatePassword(passwordDto);
    }
}
