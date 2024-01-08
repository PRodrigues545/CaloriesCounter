package ipca.projeto.caloriescounter

data class Alimento (
    var nome : String?,
    var cal : Double,
    var fat : Double,
    var carb : Double,
    var sugar : Double,
    var protein : Double,
    var sodium : Double,
    var fiber : Double,
    var imageUrl : String?,
    var quantity: Int = 0 // Default quantity is 0 grams
)