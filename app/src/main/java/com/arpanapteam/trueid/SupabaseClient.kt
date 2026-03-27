package com.arpanapteam.trueid

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import kotlinx.serialization.Serializable

// ==========================================
// 🔴 MAIN APP SUPABASE CONNECTION
// ==========================================
val supabase = createSupabaseClient(
    supabaseUrl = "https://iwlzrddbihvvzxhghgrt.supabase.co", // 👈 Yahan apna asli URL paste karein
    supabaseKey = "sb_publishable_PseXbACWYTS-Z8HrvAuflg_-AEFPHCR"  // 👈 Yahan apni asli Key paste karein
) {
    install(Postgrest)
}

// ==========================================
// 📦 DATA MODELS (Jisse app data fetch karegi)
// ==========================================
@Serializable
data class AdminServiceModel(val id: Int? = null, val title: String, val description: String, val category: String? = null, val service_key: String, val created_at: String? = null)

@Serializable
data class AdminHomeServiceModel(val id: Int? = null, val title: String, val category: String? = null, val service_key: String, val created_at: String? = null)

@Serializable
data class AdminNewsModel(val id: Int? = null, val tag: String, val date: String, val title: String, val short_content: String, val full_content: String, val image_url: String, val created_at: String? = null)

@Serializable
data class FeedbackModel(val id: Int? = null, val name: String, val email: String, val rating: Int, val message: String, val created_at: String? = null)

@Serializable
data class ServiceLinkModel(val id: Int? = null, val service_key: String, val button_title: String, val url: String, val created_at: String? = null)

@Serializable
data class ServiceInfoModel(val id: Int? = null, val service_key: String, val heading: String, val details: String, val created_at: String? = null)

@Serializable
data class AdminTravelServiceModel(
    val id: Int? = null, val service_type: String, val title: String, val subtitle: String? = null,
    val link_1: String? = null, val link_2: String? = null, val link_3: String? = null, val link_4: String? = null
)