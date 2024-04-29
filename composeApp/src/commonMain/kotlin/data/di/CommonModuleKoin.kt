package data.di

import audio_player.AudioPlayerController
import data.SongsRepository
import data.SongsRepositoryImpl
import data.SongsViewModel
import org.koin.core.module.Module
import org.koin.dsl.module

expect val commonModule: Module