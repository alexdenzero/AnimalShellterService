package pro.sky.animalizer.model;


public enum ButtonType {
    CAT_SHELTER("cat's shelter"),
    DOG_SHELTER("dog's shelter"),
    CAT_SHELTER_INFO("cat's shelter info"),
    DOG_SHELTER_INFO("dog's shelter info"),
    CAT_SHELTER_INFORMATION("cat's shelter information"),
    DOG_SHELTER_INFORMATION("dog's shelter information"),
    CAT_SHELTER_SCHEDULE("cat's shelter schedule"),
    DOG_SHELTER_SCHEDULE("dog's shelter schedule"),
    CAT_SHELTER_ADDRESS("cat's shelter address"),
    DOG_SHELTER_ADDRESS("dog's shelter address"),
    CAT_DIRECTION_PATH("cat's direction path"),
    DOG_DIRECTION_PATH("dog's direction path"),
    CAT_SECURITY_CONTACT("cat's security contact"),
    DOG_SECURITY_CONTACT("dog's security contact"),
    CAT_SAFETY_MEASURES("cat's safety measures"),
    DOG_SAFETY_MEASURES("dog's safety measures"),
    GET_PERSONAL_INFO("get personal info"),
    CAT_ADOPTION_INFO("cat adoption info"),
    DOG_ADOPTION_INFO("dog adoption info"),
    REPORT_SENDING("report sending"),
    VOLUNTEER_CALLING("volunteer calling");

    private final String label;

    ButtonType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
