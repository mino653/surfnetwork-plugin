# SurfNetwork (Paper plugin)

Minimal Paper plugin including:
- Vault economy integration
- Homes (/sethome, /home)
- Chunk claims (/claim, /trust, /unclaim)
- Clickable NPC teleport (entity named 'Survival NPC' teleports to SurvivalWorld spawn)
- Scoreboard showing balance and info
- /top for richest online players
- /spawn with 3s delay
- Ore mining rewards (diamond, gold, iron)

Requirements:
- Paper server (latest)
- Vault
- An economy plugin compatible with Vault
- Multiverse-Core to create SurvivalWorld: `/mv create SurvivalWorld normal`

Build:
- `mvn package` (Java 17)
- Place resulting JAR into server `plugins/`.
