package com.hotelka.moscowrealtime.presentation.navigation

sealed class Destination(val route: String) {
    data object RootGraph : Destination("root_graph")

    data object MainGraph : Destination("main_graph")
    data object Home : Destination("home")
    data object Discovers : Destination("discovers")
    data object Quests : Destination("quests")
    data object QuestsLibrary : Destination("quests_library")
    data object CurrentUserProfile : Destination("curr_user_profile")

    data object AuthGraph: Destination("auth_graph")
    data object Authorization : Destination("authorization")
    data object SignIn : Destination("sign_in")
    data object SignUp : Destination("sign_up")
    data object EmailVerification : Destination("email_verification")
    data object SeeYouNextTime : Destination("see_you_next_time")

    data object CameraScanner : Destination("scanner")
    object SearchUsersPage : Destination("search_users_page")
    data object QuestGraph : Destination("quest_graph")

    data object QuestPage : Destination("quest_page/{questId}") {
        fun create(questId: String) = "quest_page/$questId"
    }

    data object UserProfilePage : Destination("profile/{userId}") {
        fun create(userId: String) = "profile/$userId"
    }

    object EventGraph : Destination("event_graph/{eventId}"){
        fun create(eventId: Int) = "event_graph/$eventId"
    }
    data object EventPage : Destination("event_page/{eventId}") {
        fun create(eventId: Int) = "event_page/$eventId"
    }
    data object CurrEventMapRoute : Destination("curr_event_map_route_page")
    data object OrganizationGraph : Destination("organization_graph")
    data object OrganizationPage : Destination("organization_page")
    data object GroupPage : Destination("group_page")
    data object OrganizerPage : Destination("organizer_page")
    data object ClientGroups : Destination("client_groups")


    data object QuestProgressGraph : Destination("quest_progress_graph")
    data object CurrQuestProgressPage : Destination("curr_quest_progress_state")
    data object CurrQuestInteractiveMap : Destination("curr_quest_interactive_map")

    object QuestConstructorGraph : Destination("quest_constructor_graph")
    object QuestConstructorPage: Destination("quest_constructor")
}