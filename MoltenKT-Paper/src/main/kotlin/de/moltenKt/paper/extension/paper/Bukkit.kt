@file:Suppress("unused")

package de.moltenKt.paper.extension.paper

import de.moltenKt.core.extension.classType.UUID
import de.moltenKt.core.tool.smart.identification.Identifiable
import de.moltenKt.paper.structure.app.App
import de.moltenKt.unfold.extension.KeyingStrategy.CONTINUE
import de.moltenKt.unfold.extension.subKey
import net.kyori.adventure.key.Key
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.NamespacedKey
import org.bukkit.OfflinePlayer
import org.bukkit.Server
import org.bukkit.World
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin

/**
 * This value represents the [ConsoleCommandSender] (the console).
 * This uses the [Bukkit.getConsoleSender] function and is initialized lazily.
 * @author Fruxz
 * @since 1.0
 */
val consoleSender: ConsoleCommandSender by lazy { Bukkit.getConsoleSender() }

/**
 * This computational value returns every online-player
 * inside a [Set] of [Player]s.
 * This utilizes the [Bukkit.getOnlinePlayers] function
 * and converts it into a set using the [Collection.toSet]
 * function.
 * @author Fruxz
 * @since 1.0
 */
val onlinePlayers: Set<Player>
	get() = Bukkit.getOnlinePlayers().toSet()

/**
 * This computational value returns every offline-player
 * inside a [Set] of [OfflinePlayer]s.
 * **This operation can be quite performance-intensive, if
 * you had a lot of unique players on your server, you should
 * directly use the [Bukkit.getOfflinePlayers] function!**
 * This utilizes the [Bukkit.getOfflinePlayers] function
 * and converts it into a set using the [Array.toSet]
 * function.
 * @author Fruxz
 * @since 1.0
 */
val offlinePlayers: Set<OfflinePlayer>
	get() = Bukkit.getOfflinePlayers().toSet()

/**
 * This function searches for an online-[Player], which
 * has the given [playerName] as name.
 * This returns the found online-[Player], or null, if
 * no player with the given name is online.
 * @author Fruxz
 * @since 1.0
 */
fun playerOrNull(playerName: String) = Bukkit.getPlayer(playerName)

/**
 * This function searches for an online-[Player], which
 * has the given [uniqueIdentity] as unique identity.
 * This returns the found online-[Player], or null, if
 * no player with the given unique identity is online.
 * @author Fruxz
 * @since 1.0
 */
fun playerOrNull(uniqueIdentity: UUID) = Bukkit.getPlayer(uniqueIdentity)

/**
 * This function searches for an online-[Player], which
 * has the given [identity] as identity.
 * This returns the found online-[Player], or null, if
 * no player with the given identity is online.
 * @author Fruxz
 * @since 1.0
 */
fun playerOrNull(identity: Identifiable<out OfflinePlayer>) = playerOrNull(UUID.fromString(identity.identity))

/**
 * This function searches for an online-[Player], which
 * has the given [playerName] as name.
 * This returns the found online-[Player], or throws an
 * [NoSuchElementException] if no player with the given
 * name is online.
 * @author Fruxz
 * @since 1.0
 */
fun player(playerName: String) = playerOrNull(playerName) ?: throw NoSuchElementException("Player '$playerName'(Name) not found")

/**
 * This function searches for an online-[Player], which
 * has the given [uniqueIdentity] as unique identity.
 * This returns the found online-[Player], or throws an
 * [NoSuchElementException] if no player with the given
 * unique identity is online.
 * @author Fruxz
 * @since 1.0
 */
fun player(uniqueIdentity: UUID) = playerOrNull(uniqueIdentity) ?: throw NoSuchElementException("Player '$uniqueIdentity'(UUID) not found")

/**
 * This function searches for an online-[Player], which
 * has the given [identity] as identity.
 * This returns the found online-[Player], or throws an
 * [NoSuchElementException] if no player with the given
 * identity is online.
 * @author Fruxz
 * @since 1.0
 */
fun player(identity: Identifiable<out OfflinePlayer>) = playerOrNull(identity) ?: throw NoSuchElementException("Player '$identity'(Identity) not found")

/**
 * This function searches for an [OfflinePlayer], which
 * has the given [playerName] and returns it.
 * @author Fruxz
 * @since 1.0
 */
fun offlinePlayer(playerName: String) = Bukkit.getOfflinePlayer(playerName)

/**
 * This function searches for an [OfflinePlayer], which
 * has the given [uniqueIdentity] and returns it.
 * @author Fruxz
 * @since 1.0
 */
fun offlinePlayer(uniqueIdentity: UUID) = Bukkit.getOfflinePlayer(uniqueIdentity)

/**
 * This function searches for an [OfflinePlayer], which
 * has the given [identity] and returns it.
 * @author Fruxz
 * @since 1.0
 */
fun offlinePlayer(identity: Identifiable<out OfflinePlayer>) = offlinePlayer(UUID.fromString(identity.identity))

/**
 * This function creates a [NamespacedKey] with the given
 * [key] on [this] [Plugin]s base.
 * @author Fruxz
 * @since 1.0
 */
fun Plugin.createNamespacedKey(key: String): NamespacedKey = NamespacedKey(this, key)

fun App.createKey(value: String): Key = key.subKey(value, CONTINUE)

/**
 * This function searches for a [World] with the given
 * [worldName] as name.
 * This returns the found [World], or null, if no
 * world with the given name is found.
 * @author Fruxz
 * @since 1.0
 */
fun worldOrNull(worldName: String) = Bukkit.getWorld(worldName)

/**
 * This function searches for a [World] with the given
 * [uniqueIdentity] as unique identity.
 * This returns the found [World], or null, if no
 * world with the given unique identity is found.
 * @author Fruxz
 * @since 1.0
 */
fun worldOrNull(uniqueIdentity: UUID) = Bukkit.getWorld(uniqueIdentity)

/**
 * This function searches for a [World] with the given
 * [worldKey] as key.
 * This returns the found [World], or null, if no
 * world with the given key is found.
 * @author Fruxz
 * @since 1.0
 */
fun worldOrNull(worldKey: NamespacedKey) = Bukkit.getWorld(worldKey)

/**
 * This function searches for a [World] with the given
 * [worldName] as name.
 * This returns the found [World], or throws an
 * [NoSuchElementException] if no world with the given
 * name is found.
 * @author Fruxz
 * @since 1.0
 */
fun world(worldName: String) = worldOrNull(worldName) ?: throw NoSuchElementException("World '$worldName'(Name) not found")

/**
 * This function searches for a [World] with the given
 * [uniqueIdentity] as unique identity.
 * This returns the found [World], or throws an
 * [NoSuchElementException] if no world with the given
 * unique identity is found.
 * @author Fruxz
 * @since 1.0
 */
fun world(uniqueIdentity: UUID) = worldOrNull(uniqueIdentity) ?: throw NoSuchElementException("World '$uniqueIdentity'(UUID) not found")

/**
 * This function searches for a [World] with the given
 * [worldKey] as key.
 * This returns the found [World], or throws an
 * [NoSuchElementException] if no world with the given
 * key is found.
 * @author Fruxz
 * @since 1.0
 */
fun world(worldKey: NamespacedKey) = worldOrNull(worldKey) ?: throw NoSuchElementException("World '$worldKey'(Key) not found")

/**
 * This computational value returns the [World.getSpawnLocation]
 * of the [worlds] first [World].
 * This helps to quickly get an easy-to-use [Location], that
 * doesn't have to be something particularly special.
 * Commonly used as parameter- & config-defaults
 * @author Fruxz
 * @since 1.0
 */
val templateLocation: Location
	get() = worlds.first().spawnLocation

/**
 * This computational value returns a [List] of [World]s,
 * using the [Bukkit.getWorlds] function.
 * @author Fruxz
 * @since 1.0
 */
val worlds: List<World>
	get() = Bukkit.getWorlds()

/**
 * This computational value returns the [Server], on which
 * this app is currently running, using the [Bukkit.getServer]
 * function.
 * @author Fruxz
 * @since 1.0
 */
val server: Server
	get() = Bukkit.getServer()