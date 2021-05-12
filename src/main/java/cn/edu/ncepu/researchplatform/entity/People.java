package cn.edu.ncepu.researchplatform.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.validation.annotation.Validated;

@Validated
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class People {
}
