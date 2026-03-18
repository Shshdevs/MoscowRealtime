package com.hotelka.moscowrealtime.di

import com.hotelka.moscowrealtime.data.remote.AuthDataSource
import com.hotelka.moscowrealtime.data.remote.AuthDataSourceImpl
import com.hotelka.moscowrealtime.data.remote.DiscoveriesDataSource
import com.hotelka.moscowrealtime.data.remote.DiscoveriesDataSourceImpl
import com.hotelka.moscowrealtime.data.remote.FriendRequestDataSource
import com.hotelka.moscowrealtime.data.remote.FriendRequestDataSourceImpl
import com.hotelka.moscowrealtime.data.remote.GroupDataSource
import com.hotelka.moscowrealtime.data.remote.GroupDataSourceImpl
import com.hotelka.moscowrealtime.data.remote.KudaGoDataSource
import com.hotelka.moscowrealtime.data.remote.KudaGoDataSourceImpl
import com.hotelka.moscowrealtime.data.remote.LocationDataSource
import com.hotelka.moscowrealtime.data.remote.LocationDataSourceImpl
import com.hotelka.moscowrealtime.data.remote.MRTApiDataSource
import com.hotelka.moscowrealtime.data.remote.MRTApiDataSourceImpl
import com.hotelka.moscowrealtime.data.remote.OSRMDataSource
import com.hotelka.moscowrealtime.data.remote.OSRMDataSourceImpl
import com.hotelka.moscowrealtime.data.remote.OrganizationDataSource
import com.hotelka.moscowrealtime.data.remote.OrganizationDataSourceImpl
import com.hotelka.moscowrealtime.data.remote.OrganizationInvitationDataSource
import com.hotelka.moscowrealtime.data.remote.OrganizationInvitationDataSourceImpl
import com.hotelka.moscowrealtime.data.remote.QuestDataSource
import com.hotelka.moscowrealtime.data.remote.QuestDataSourceImpl
import com.hotelka.moscowrealtime.data.remote.QuestInvitationDataSource
import com.hotelka.moscowrealtime.data.remote.QuestInvitationDataSourceImpl
import com.hotelka.moscowrealtime.data.remote.QuestProgressDataSource
import com.hotelka.moscowrealtime.data.remote.QuestProgressDataSourceImpl
import com.hotelka.moscowrealtime.data.remote.SearchDataSource
import com.hotelka.moscowrealtime.data.remote.SearchDataSourceImpl
import com.hotelka.moscowrealtime.data.remote.UploadPhotoDataSource
import com.hotelka.moscowrealtime.data.remote.UploadPhotoDataSourceImpl
import com.hotelka.moscowrealtime.data.remote.UserDataSource
import com.hotelka.moscowrealtime.data.remote.UserDataSourceImpl
import com.hotelka.moscowrealtime.data.remote.ScoreDataSource
import com.hotelka.moscowrealtime.data.remote.ScoreDataSourceImpl
import com.hotelka.moscowrealtime.data.mapper.LocationMapper
import com.hotelka.moscowrealtime.data.mapper.QuestMapper
import com.hotelka.moscowrealtime.data.repository.AuthRepositoryImpl
import com.hotelka.moscowrealtime.data.repository.DiscoveriesRepositoryImpl
import com.hotelka.moscowrealtime.data.repository.FriendRequestRepositoryImpl
import com.hotelka.moscowrealtime.data.repository.GroupRepositoryImpl
import com.hotelka.moscowrealtime.data.repository.KudaGoRepositoryImpl
import com.hotelka.moscowrealtime.data.repository.LocationsRepositoryImpl
import com.hotelka.moscowrealtime.data.repository.MRTApiRepositoryImpl
import com.hotelka.moscowrealtime.data.repository.OSRMRepositoryImpl
import com.hotelka.moscowrealtime.data.repository.OrganizationInvitationRepositoryImpl
import com.hotelka.moscowrealtime.data.repository.OrganizationRepositoryImpl
import com.hotelka.moscowrealtime.data.repository.QuestInvitationRepositoryImpl
import com.hotelka.moscowrealtime.data.repository.QuestProgressRepositoryImpl
import com.hotelka.moscowrealtime.data.repository.QuestRepositoryImpl
import com.hotelka.moscowrealtime.data.repository.SearchRepositoryImpl
import com.hotelka.moscowrealtime.data.repository.UploadPhotoRepositoryImpl
import com.hotelka.moscowrealtime.data.repository.UserRepositoryImpl
import com.hotelka.moscowrealtime.data.repository.ScoreRepositoryImpl
import com.hotelka.moscowrealtime.domain.repository.AuthRepository
import com.hotelka.moscowrealtime.domain.repository.DiscoveriesRepository
import com.hotelka.moscowrealtime.domain.repository.FriendRequestRepository
import com.hotelka.moscowrealtime.domain.repository.GroupRepository
import com.hotelka.moscowrealtime.domain.repository.KudaGoRepository
import com.hotelka.moscowrealtime.domain.repository.LocationsRepository
import com.hotelka.moscowrealtime.domain.repository.MRTApiRepository
import com.hotelka.moscowrealtime.domain.repository.OSRMRepository
import com.hotelka.moscowrealtime.domain.repository.OrganizationInvitationRepository
import com.hotelka.moscowrealtime.domain.repository.OrganizationRepository
import com.hotelka.moscowrealtime.domain.repository.QuestInvitationRepository
import com.hotelka.moscowrealtime.domain.repository.QuestProgressRepository
import com.hotelka.moscowrealtime.domain.repository.QuestRepository
import com.hotelka.moscowrealtime.domain.repository.SearchRepository
import com.hotelka.moscowrealtime.domain.repository.UploadPhotoRepository
import com.hotelka.moscowrealtime.domain.repository.UserRepository
import com.hotelka.moscowrealtime.domain.repository.ScoreRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<KudaGoDataSource> { KudaGoDataSourceImpl(get(), get()) }
    single<KudaGoRepository> { KudaGoRepositoryImpl(get()) }

    single<MRTApiDataSource> { MRTApiDataSourceImpl(get()) }
    single<MRTApiRepository> { MRTApiRepositoryImpl(get(), LocationMapper(get())) }

    single<OSRMDataSource> { OSRMDataSourceImpl(get()) }
    single<OSRMRepository> { OSRMRepositoryImpl(get()) }

    single<UploadPhotoDataSource> { UploadPhotoDataSourceImpl(get()) }
    single<UploadPhotoRepository> { UploadPhotoRepositoryImpl(get()) }

    single<UserDataSource> { UserDataSourceImpl(get()) }
    single<UserRepository> { UserRepositoryImpl(get()) }

    single<AuthDataSource> { AuthDataSourceImpl(get()) }
    single<AuthRepository> { AuthRepositoryImpl(get()) }

    single<ScoreDataSource> { ScoreDataSourceImpl(get()) }
    single<ScoreRepository> { ScoreRepositoryImpl(get()) }

    single<LocationDataSource> { LocationDataSourceImpl(get()) }
    single<LocationsRepository> { LocationsRepositoryImpl(LocationMapper(get()), get()) }

    single<DiscoveriesDataSource> { DiscoveriesDataSourceImpl(get()) }
    single<DiscoveriesRepository> { DiscoveriesRepositoryImpl(get()) }

    single<QuestDataSource> { QuestDataSourceImpl(get()) }
    single<QuestRepository> { QuestRepositoryImpl(get(), QuestMapper(get())) }

    single<QuestProgressDataSource> { QuestProgressDataSourceImpl(get()) }
    single<QuestProgressRepository> { QuestProgressRepositoryImpl(get()) }

    single<QuestInvitationDataSource> { QuestInvitationDataSourceImpl(get()) }
    single<QuestInvitationRepository> { QuestInvitationRepositoryImpl(get()) }

    single<FriendRequestDataSource> { FriendRequestDataSourceImpl(get()) }
    single<FriendRequestRepository> { FriendRequestRepositoryImpl(get()) }

    single<SearchDataSource> { SearchDataSourceImpl(get()) }
    single<SearchRepository> { SearchRepositoryImpl(get(), QuestMapper(get()), LocationMapper(get())) }

    single<OrganizationDataSource> { OrganizationDataSourceImpl(get()) }
    single<OrganizationRepository> { OrganizationRepositoryImpl(get()) }

    single<OrganizationInvitationDataSource> { OrganizationInvitationDataSourceImpl(get()) }
    single<OrganizationInvitationRepository> { OrganizationInvitationRepositoryImpl(get()) }

    single<GroupDataSource> { GroupDataSourceImpl(get()) }
    single<GroupRepository> { GroupRepositoryImpl(get()) }
}