package jose.gonzalez.mobiquitysample.model

data class MobiquityModel(
    val id: String,
    val name: String,
    val description: String,
    val products: List<ProductModel>
)