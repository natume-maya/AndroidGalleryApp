package com.example.data.api.result

import com.squareup.moshi.Json

data class CocktailResult(
        val status: String,
        @Json(name = "total_pages")
        val totalPages: Int,
        @Json(name = "current_page")
        val currentPage: Int,
        val cocktails: Array<CocktailsResult>
)

data class CocktailsResult(
        @Json(name = "cocktail_id")
        val cocktailId: Int,
        @Json(name = "cocktail_name")
        val cocktailName: String,
        @Json(name = "cocktail_name_english")
        val cocktailNameEnglish: String,
        @Json(name = "base_name")
        val baseName: String,
        @Json(name = "technique_name")
        val techniqueName: String,
        @Json(name = "taste_name")
        val tasteName: String,
        @Json(name = "style_name")
        val styleName: String,
        val alcohol: Int,
        @Json(name = "top_name")
        val topName: String,
        @Json(name = "glass_name")
        val glassName: String,
        @Json(name = "type_name")
        val typeName: String,
        @Json(name = "cocktail_digest")
        val cocktailDigest: String,
        @Json(name = "cocktail_desc")
        val cocktailDescription: String,
        @Json(name = "recipe_desc")
        val recipeDescription: String,
        val recipes: Array<RecipesResult>
)

data class RecipesResult(
        @Json(name = "ingredient_id")
        val ingredientId: Int,
        @Json(name = "ingredient_name")
        val ingredientName: String,
        val amount: String,
        val unit: String
)
