# NightTerror (Forge 1.20.1)

NightTerror is a Forge 1.20.1 horror-focused gameplay mod that introduces two atmospheric entities, a defensive repellent, and immersive audiovisual overlays. The project is designed as a safe, self-contained experience that respects Minecraft worlds and players.

## Features

- **Echoer** – A late-night roaming monster that targets nearby players, applies darkness, and brings a chilling soundscape. It despawns safely at sunrise and always drops an Echo Fragment.
- **Dr. Halving** – A neutral apparition used for encounters or storytelling. It appears briefly, delivers a distorted voice line, triggers a flickering overlay, and disappears without harming anything.
- **Echo Repellent** – A craftable vial that creates an invisible field blocking Echoer spawns for several minutes, providing a strategic respite.
- **Client Effects** – Custom vignette and flicker overlays coupled with bespoke placeholder soundscapes to reinforce each encounter without altering core gameplay.
- **Configurable Gameplay** – Toggle Echoer behaviour, tune spawn chance, darkness duration, and repellent potency from the included Forge config.

## Installation

1. Install **Minecraft Forge 1.20.1**.
2. Download or build the NightTerror mod JAR with `./gradlew build`.
3. Place the resulting `nightterror-*.jar` into the Minecraft `mods` folder.
4. Launch Minecraft Forge and ensure NightTerror appears in the mods list.

## Configuration

Forge automatically generates `nightterror-common.toml` on first launch. The key options include:

- `enableEchoer`: Master toggle for Echoer spawns.
- `spawnChance`: Per-player tick chance to summon an Echoer when conditions are met (default `0.0004`).
- `darknessDurationTicks`: Darkness effect length applied on spawn.
- `enableRepellent`: Toggles the Echo Repellent item and field generation.
- `repellentDurationTicks` & `repellentRadius`: Control how long and how far a repellent field suppresses Echoer spawns.

Change the values, save the config, and restart the game/server to apply.

## Safety & Scope

- No gameplay bans, world edits, or file operations outside Minecraft directories.
- No custom networking beyond Forge’s channel system.
- All assets are original placeholders created for this project.
- Every effect is reversible and focuses on ambience over permanent world change.

## Credits

- Placeholder design & implementation: Xiory
- Sound and texture prompts for further iteration are documented in `SOUND_PROMPTS.txt` and `TEXTURE_PROMPTS.md`.
