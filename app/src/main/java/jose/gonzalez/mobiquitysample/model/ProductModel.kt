package jose.gonzalez.mobiquitysample.model

data class ProductModel(
    val id: String,
    val categoryId: String,
    val name: String,
    val url: String,
    val description: String,
    val salePrice: SalePriceModel
)