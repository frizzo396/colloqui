package com.accenture.interview.utils.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.accenture.interview.rto.BaseTypeRTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The Enum SiteEnum.
 */
@Getter
@AllArgsConstructor
public enum SiteEnum
{

   /** The cagliari. */
   CAGLIARI(1, "Cagliari"),

   /** The napoli. */
   NAPOLI(2, "Napoli"),

   /** The bari. */
   BARI(3, "Bari"),

   /** The roma. */
   ROMA(4, "Roma"),

   /** The milano. */
   MILANO(5, "Milano");

   /** The id. */
   private Integer id;

   /** The description. */
   private String description;

   /**
    * Gets the id from description.
    *
    * @param description the description
    * @return the id from description
    */
   public static Integer getIdFromDescription(String description) {
      return Arrays.stream(SiteEnum.values())
            .filter(a -> a.description.equals(description))
            .map(SiteEnum::getId)
            .findFirst().orElse(null);
   }

   /**
    * Gets the type list.
    *
    * @return the type list
    */
   public static List<BaseTypeRTO> getTypeList() {
      return Arrays.stream(SiteEnum.values())
            .map(a -> new BaseTypeRTO(Long.parseLong(a.getId().toString()), a.getDescription()))
            .collect(Collectors.toList());
   }
}
