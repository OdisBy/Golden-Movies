package com.odisby.goldentomatoes.feature.details.ui

import androidx.lifecycle.ViewModel

class DetailsViewModel : ViewModel() {

}

data class DetailsUiState(
    val movieName: String = "Meu Malvado Favorito 4",
    val movieDescription: String = "Nesta sequência, o vilão mais amado do planeta retorna e agora Gru, Lucy, Margo, Edith e Agnes dão as boas-vindas a um novo membro da família: Gru Jr., que pretende atormentar seu pai. Enquanto se adapta com o pequeno, Gru enfrenta um novo inimigo, Maxime Le Mal, forçando sua namorada Valentina e a família a fugir do perigo. Nesta sequência, o vilão mais amado do planeta retorna e agora Gru, Lucy, Margo, Edith e Agnes dão as boas-vindas a um novo membro da família: Gru Jr., que pretende atormentar seu pai. Enquanto se adapta com o pequeno, Gru enfrenta um novo inimigo, Maxime Le Mal, forçando sua namorada Valentina e a família a fugir do perigo.",
    val movieImageLink: String? = null,
    val scheduled: Boolean = false,
    val expectations: Int? = 0
)
