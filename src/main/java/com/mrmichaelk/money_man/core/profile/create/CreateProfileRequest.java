package com.mrmichaelk.money_man.core.profile.create;

import lombok.*;

@Builder 
@Data 
@AllArgsConstructor 
@NoArgsConstructor 
public class CreateProfileRequest {
    private String name;
}
