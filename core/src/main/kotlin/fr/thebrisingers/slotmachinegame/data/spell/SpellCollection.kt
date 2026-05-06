package fr.thebrisingers.slotmachinegame.data.spell
// ═══════════════════════════════════════════════════════════════════════════
// Équilibrage (recalibré)
//
// Espérance mana par spin (un élément, 5 lignes) : ~3.5
//
// Tier 1 mono   : coût  9 mana  → ~2.5 spins
// Tier 2 mono   : coût 16 mana  → ~4.5 spins
// Tier 3 mono   : coût 28 mana  → ~8   spins
//
// Hybrides : chaque élément est contraint indépendamment,
//            coûts symétriques = demi-coût correspondant au tier
// Tier 1 hybride : 4+4   (~2.5 spins pour chaque élément)
// Tier 2 hybride : 8+8   (~4.5 spins pour chaque élément)
// Tier 3 hybride : 14+14 (~8   spins pour chaque élément)
//
// Référence monstres (difficultyMultiplier=1) :
//   ARCHER   HP=8-11  DMG=5  ATK/3-5 tours
//   ASSASSIN HP=7-9   DMG=4  ATK/2-4 tours
//   SOLDIER  HP=14-16 DMG=8  ATK/4-6 tours
//
// Dégâts cibles :
//   FRONT Tier 1 : 8-10  (tuer un monstre faible, entamer un fort)
//   FRONT Tier 2 : 14-16 (tuer la plupart des monstres)
//   FRONT Tier 3 : 22-25 (tuer n'importe quel monstre, voire 2)
//   ALL   Tier 1 : 4-5   (réduit car multi-cible)
//   ALL   Tier 2 : 8-10
//   ALL   Tier 3 : 13-16
//
// HERO_MAX_HEALTH = 50
// ═══════════════════════════════════════════════════════════════════════════

val spellCollection = listOf(

    // ───────────────────────────────────────────────────────────────────────
    // 🔥 FEU
    // ───────────────────────────────────────────────────────────────────────

    Spell(
        tier = SpellTier.TIER_1,
        cost = SpellCost(fireCost = 9),
        name = "Étincelle",
        description = "Lance une étincelle sur le premier ennemi",
        spellEffect = listOf(SpellEffect(target = Target.FRONT, value = -9)),
    ),
    Spell(
        tier = SpellTier.TIER_1,
        cost = SpellCost(fireCost = 9),
        name = "Souffle ardent",
        description = "Exhale une bouffée de flammes sur tous les ennemis",
        spellEffect = listOf(SpellEffect(target = Target.ALL, value = -4)),
    ),
    Spell(
        tier = SpellTier.TIER_2,
        cost = SpellCost(fireCost = 16),
        name = "Boule de feu",
        description = "Lance une boule de feu sur le premier ennemi",
        spellEffect = listOf(SpellEffect(target = Target.FRONT, value = -15)),
    ),
    Spell(
        tier = SpellTier.TIER_2,
        cost = SpellCost(fireCost = 16),
        name = "Torche vivante",
        description = "Enflamme le premier ennemi pour des dégâts importants",
        spellEffect = listOf(SpellEffect(target = Target.FRONT, value = -16)),
    ),
    Spell(
        tier = SpellTier.TIER_3,
        cost = SpellCost(fireCost = 28),
        name = "Météore",
        description = "Fait s'écraser un rocher enflammé sur tous les ennemis",
        spellEffect = listOf(SpellEffect(target = Target.ALL, value = -14)),
    ),
    Spell(
        tier = SpellTier.TIER_3,
        cost = SpellCost(fireCost = 28),
        name = "Immolation",
        description = "Déchaîne une explosion de feu massive, au prix de sa propre chair",
        spellEffect = listOf(
            SpellEffect(target = Target.ALL, value = -20),
            SpellEffect(target = Target.SELF, value = -10),
        ),
    ),

    // ───────────────────────────────────────────────────────────────────────
    // 💧 EAU
    // ───────────────────────────────────────────────────────────────────────

    Spell(
        tier = SpellTier.TIER_1,
        cost = SpellCost(waterCost = 9),
        name = "Gouttelette",
        description = "Projette un jet d'eau sur le premier ennemi",
        spellEffect = listOf(SpellEffect(target = Target.FRONT, value = -9)),
    ),
    Spell(
        tier = SpellTier.TIER_1,
        cost = SpellCost(waterCost = 9),
        name = "Soin aquatique",
        description = "Une eau pure restaure les PV du lanceur",
        spellEffect = listOf(SpellEffect(target = Target.SELF, value = 10)),
    ),
    Spell(
        tier = SpellTier.TIER_2,
        cost = SpellCost(waterCost = 16),
        name = "Petite vague",
        description = "Lance une vague sur tous les ennemis",
        spellEffect = listOf(SpellEffect(target = Target.ALL, value = -9)),
    ),
    Spell(
        tier = SpellTier.TIER_2,
        cost = SpellCost(waterCost = 16),
        name = "Jet d'eau",
        description = "Propulse un puissant jet d'eau sur le premier ennemi",
        spellEffect = listOf(SpellEffect(target = Target.FRONT, value = -15)),
    ),
    Spell(
        tier = SpellTier.TIER_3,
        cost = SpellCost(waterCost = 28),
        name = "Raz-de-marée",
        description = "Une vague dévastatrice déferle sur tous les ennemis",
        spellEffect = listOf(SpellEffect(target = Target.ALL, value = -15)),
    ),
    Spell(
        tier = SpellTier.TIER_3,
        cost = SpellCost(waterCost = 28),
        name = "Grand soin aquatique",
        description = "Une puissante eau de vie restaure massivement les PV du lanceur",
        spellEffect = listOf(SpellEffect(target = Target.SELF, value = 25)),
    ),

    // ───────────────────────────────────────────────────────────────────────
    // 🌿 NATURE
    // ───────────────────────────────────────────────────────────────────────

    Spell(
        tier = SpellTier.TIER_1,
        cost = SpellCost(earthCost = 9),
        name = "Vrille",
        description = "Une liane fouette le premier ennemi",
        spellEffect = listOf(SpellEffect(target = Target.FRONT, value = -9)),
    ),
    Spell(
        tier = SpellTier.TIER_1,
        cost = SpellCost(earthCost = 9),
        name = "Nuée d'épines",
        description = "Projette une pluie d'épines sur tous les ennemis",
        spellEffect = listOf(SpellEffect(target = Target.ALL, value = -4)),
    ),
    Spell(
        tier = SpellTier.TIER_2,
        cost = SpellCost(earthCost = 16),
        name = "Régénération",
        description = "La nature restaure généreusement les PV du lanceur",
        spellEffect = listOf(SpellEffect(target = Target.SELF, value = 16)),
    ),
    Spell(
        tier = SpellTier.TIER_2,
        cost = SpellCost(earthCost = 16),
        name = "Étreinte sylvestre",
        description = "Des racines surgissent et broient le dernier ennemi",
        spellEffect = listOf(SpellEffect(target = Target.BACK, value = -15)),
    ),
    Spell(
        tier = SpellTier.TIER_3,
        cost = SpellCost(earthCost = 28),
        name = "Forêt hostile",
        description = "Des ronces surgissent sous tous les ennemis",
        spellEffect = listOf(SpellEffect(target = Target.ALL, value = -14)),
    ),
    Spell(
        tier = SpellTier.TIER_3,
        cost = SpellCost(earthCost = 28),
        name = "Soulèvement rocheux",
        description = "Un soulèvement rocheux frappe le premier et le dernier ennemi",
        spellEffect = listOf(
            SpellEffect(target = Target.FRONT, value = -16),
            SpellEffect(target = Target.BACK, value = -16),
        ),
    ),

    // ───────────────────────────────────────────────────────────────────────
    // 💨 AIR
    // ───────────────────────────────────────────────────────────────────────

    Spell(
        tier = SpellTier.TIER_1,
        cost = SpellCost(windCost = 9),
        name = "Coup de vent",
        description = "Un souffle de vent frappe le premier ennemi",
        spellEffect = listOf(SpellEffect(target = Target.FRONT, value = -9)),
    ),
    Spell(
        tier = SpellTier.TIER_1,
        cost = SpellCost(windCost = 9),
        name = "Petite bourrasque",
        description = "Une bourrasque frappe le dernier ennemi",
        spellEffect = listOf(SpellEffect(target = Target.BACK, value = -9)),
    ),
    Spell(
        tier = SpellTier.TIER_2,
        cost = SpellCost(windCost = 16),
        name = "Tranchant du vent",
        description = "Une lame d'air comprimé perce le premier ennemi",
        spellEffect = listOf(SpellEffect(target = Target.FRONT, value = -15)),
    ),
    Spell(
        tier = SpellTier.TIER_2,
        cost = SpellCost(windCost = 16),
        name = "Rafale perçante",
        description = "Une rafale frappe simultanément le premier et le dernier ennemi",
        spellEffect = listOf(
            SpellEffect(target = Target.FRONT, value = -9),
            SpellEffect(target = Target.BACK, value = -9),
        ),
    ),
    Spell(
        tier = SpellTier.TIER_3,
        cost = SpellCost(windCost = 28),
        name = "Cyclone",
        description = "Un tourbillon de vent dévastateur frappe tous les ennemis",
        spellEffect = listOf(SpellEffect(target = Target.ALL, value = -14)),
    ),
    Spell(
        tier = SpellTier.TIER_3,
        cost = SpellCost(windCost = 28),
        name = "Vortex",
        description = "Un vortex aspire et broie tous les ennemis",
        spellEffect = listOf(SpellEffect(target = Target.ALL, value = -15)),
    ),

    // ───────────────────────────────────────────────────────────────────────
    // ⚡ FOUDRE (Feu + Air)
    // ───────────────────────────────────────────────────────────────────────

    Spell(
        tier = SpellTier.TIER_1,
        cost = SpellCost(fireCost = 4, windCost = 4),
        name = "Décharge statique",
        description = "Une petite décharge électrique frappe le premier ennemi",
        spellEffect = listOf(SpellEffect(target = Target.FRONT, value = -10)),
    ),
    Spell(
        tier = SpellTier.TIER_2,
        cost = SpellCost(fireCost = 8, windCost = 8),
        name = "Éclair",
        description = "Un éclair foudroie le premier ennemi",
        spellEffect = listOf(SpellEffect(target = Target.FRONT, value = -16)),
    ),
    Spell(
        tier = SpellTier.TIER_2,
        cost = SpellCost(fireCost = 8, windCost = 8),
        name = "Arc galvanique",
        description = "Un arc électrique frappe simultanément le premier et le dernier ennemi",
        spellEffect = listOf(
            SpellEffect(target = Target.FRONT, value = -10),
            SpellEffect(target = Target.BACK, value = -10),
        ),
    ),
    Spell(
        tier = SpellTier.TIER_3,
        cost = SpellCost(fireCost = 14, windCost = 14),
        name = "Foudre divine",
        description = "Un éclair céleste s'abat violemment sur le premier ennemi",
        spellEffect = listOf(SpellEffect(target = Target.FRONT, value = -25)),
    ),
    Spell(
        tier = SpellTier.TIER_3,
        cost = SpellCost(fireCost = 14, windCost = 14),
        name = "Tempête électrique",
        description = "Une tempête électrique déferle sur tous les ennemis",
        spellEffect = listOf(SpellEffect(target = Target.ALL, value = -15)),
    ),

    // ───────────────────────────────────────────────────────────────────────
    // 🌋 MAGMA (Feu + Nature)
    // ───────────────────────────────────────────────────────────────────────

    Spell(
        tier = SpellTier.TIER_1,
        cost = SpellCost(fireCost = 4, earthCost = 4),
        name = "Crachée de lave",
        description = "Une projection de lave brûlante frappe le premier ennemi",
        spellEffect = listOf(SpellEffect(target = Target.FRONT, value = -10)),
    ),
    Spell(
        tier = SpellTier.TIER_2,
        cost = SpellCost(fireCost = 8, earthCost = 8),
        name = "Soulèvement volcanique",
        description = "Une éruption locale frappe le premier et le dernier ennemi",
        spellEffect = listOf(
            SpellEffect(target = Target.FRONT, value = -10),
            SpellEffect(target = Target.BACK, value = -10),
        ),
    ),
    Spell(
        tier = SpellTier.TIER_2,
        cost = SpellCost(fireCost = 8, earthCost = 8),
        name = "Pilier de lave",
        description = "Une colonne de magma jaillit sous le premier ennemi",
        spellEffect = listOf(SpellEffect(target = Target.FRONT, value = -17)),
    ),
    Spell(
        tier = SpellTier.TIER_3,
        cost = SpellCost(fireCost = 14, earthCost = 14),
        name = "Coulée de magma",
        description = "Un flux de lave incandescent brûle tous les ennemis",
        spellEffect = listOf(SpellEffect(target = Target.ALL, value = -14)),
    ),
    Spell(
        tier = SpellTier.TIER_3,
        cost = SpellCost(fireCost = 14, earthCost = 14),
        name = "Éruption",
        description = "Une éruption volcanique massive dévaste tous les ennemis",
        spellEffect = listOf(SpellEffect(target = Target.ALL, value = -16)),
    ),

    // ───────────────────────────────────────────────────────────────────────
    // 🧊 GLACE (Eau + Air)
    // ───────────────────────────────────────────────────────────────────────

    Spell(
        tier = SpellTier.TIER_1,
        cost = SpellCost(waterCost = 4, windCost = 4),
        name = "Givre",
        description = "Une fine couche de glace blesse le premier ennemi",
        spellEffect = listOf(SpellEffect(target = Target.FRONT, value = -10)),
    ),
    Spell(
        tier = SpellTier.TIER_2,
        cost = SpellCost(waterCost = 8, windCost = 8),
        name = "Flèche de glace",
        description = "Un projectile acéré de glace transperce le premier ennemi",
        spellEffect = listOf(SpellEffect(target = Target.FRONT, value = -16)),
    ),
    Spell(
        tier = SpellTier.TIER_2,
        cost = SpellCost(waterCost = 8, windCost = 8),
        name = "Aiguilles de givre",
        description = "Une pluie d'éclats de glace lacère le premier et le dernier ennemi",
        spellEffect = listOf(
            SpellEffect(target = Target.FRONT, value = -10),
            SpellEffect(target = Target.BACK, value = -10),
        ),
    ),
    Spell(
        tier = SpellTier.TIER_3,
        cost = SpellCost(waterCost = 14, windCost = 14),
        name = "Tempête de grêle",
        description = "Une grêle cinglante s'abat sur tous les ennemis",
        spellEffect = listOf(SpellEffect(target = Target.ALL, value = -14)),
    ),
    Spell(
        tier = SpellTier.TIER_3,
        cost = SpellCost(waterCost = 14, windCost = 14),
        name = "Blizzard",
        description = "Une tempête glaciale dévastatrice anéantit tous les ennemis",
        spellEffect = listOf(SpellEffect(target = Target.ALL, value = -16)),
    ),

    // ───────────────────────────────────────────────────────────────────────
    // 🌸 FÉÉRIQUE (Nature + Eau)
    // ───────────────────────────────────────────────────────────────────────

    Spell(
        tier = SpellTier.TIER_1,
        cost = SpellCost(earthCost = 4, waterCost = 4),
        name = "Poussière de fée",
        description = "Une poussière magique blesse tous les ennemis",
        spellEffect = listOf(SpellEffect(target = Target.ALL, value = -5)),
    ),
    Spell(
        tier = SpellTier.TIER_2,
        cost = SpellCost(earthCost = 8, waterCost = 8),
        name = "Rosée enchantée",
        description = "Une rosée magique restaure généreusement les PV du lanceur",
        spellEffect = listOf(SpellEffect(target = Target.SELF, value = 17)),
    ),
    Spell(
        tier = SpellTier.TIER_2,
        cost = SpellCost(earthCost = 8, waterCost = 8),
        name = "Floraison mystique",
        description = "Des fleurs magiques explosent sur tous les ennemis",
        spellEffect = listOf(SpellEffect(target = Target.ALL, value = -9)),
    ),
    Spell(
        tier = SpellTier.TIER_3,
        cost = SpellCost(earthCost = 14, waterCost = 14),
        name = "Pluie de pétales tranchants",
        description = "Des pétales enchantés lacèrent tous les ennemis",
        spellEffect = listOf(SpellEffect(target = Target.ALL, value = -14)),
    ),
    Spell(
        tier = SpellTier.TIER_3,
        cost = SpellCost(earthCost = 14, waterCost = 14),
        name = "Chant des nymphes",
        description = "Une mélodie dévastatrice anéantit tous les ennemis et soigne le lanceur",
        spellEffect = listOf(
            SpellEffect(target = Target.ALL, value = -12),
            SpellEffect(target = Target.SELF, value = 10),
        ),
    ),

    // ───────────────────────────────────────────────────────────────────────
    // ☀️ LUMIÈRE (Air + Nature)
    // ───────────────────────────────────────────────────────────────────────

    Spell(
        tier = SpellTier.TIER_1,
        cost = SpellCost(windCost = 4, earthCost = 4),
        name = "Rayon de lumière",
        description = "Un fin rayon lumineux frappe le premier ennemi",
        spellEffect = listOf(SpellEffect(target = Target.FRONT, value = -10)),
    ),
    Spell(
        tier = SpellTier.TIER_2,
        cost = SpellCost(windCost = 8, earthCost = 8),
        name = "Éclair radieux",
        description = "Un rayon éblouissant frappe le premier ennemi",
        spellEffect = listOf(SpellEffect(target = Target.FRONT, value = -16)),
    ),
    Spell(
        tier = SpellTier.TIER_2,
        cost = SpellCost(windCost = 8, earthCost = 8),
        name = "Aurore",
        description = "Une lumière bienfaisante soigne le lanceur et brûle tous les ennemis",
        spellEffect = listOf(
            SpellEffect(target = Target.ALL, value = -8),
            SpellEffect(target = Target.SELF, value = 7),
        ),
    ),
    Spell(
        tier = SpellTier.TIER_3,
        cost = SpellCost(windCost = 14, earthCost = 14),
        name = "Nova solaire",
        description = "Une explosion de lumière aveuglante dévaste tous les ennemis",
        spellEffect = listOf(SpellEffect(target = Target.ALL, value = -15)),
    ),
    Spell(
        tier = SpellTier.TIER_3,
        cost = SpellCost(windCost = 14, earthCost = 14),
        name = "Jugement céleste",
        description = "Un rayon de lumière divine s'abat sur le premier ennemi",
        spellEffect = listOf(SpellEffect(target = Target.FRONT, value = -25)),
    ),

    // ───────────────────────────────────────────────────────────────────────
    // 🌑 SOMBRE (Eau + Feu)
    // ───────────────────────────────────────────────────────────────────────

    Spell(
        tier = SpellTier.TIER_1,
        cost = SpellCost(waterCost = 4, fireCost = 4),
        name = "Tendrilles obscures",
        description = "Des filaments de ténèbres griffent le premier ennemi",
        spellEffect = listOf(SpellEffect(target = Target.FRONT, value = -10)),
    ),
    Spell(
        tier = SpellTier.TIER_2,
        cost = SpellCost(waterCost = 8, fireCost = 8),
        name = "Drain vital",
        description = "Absorbe la vie du premier ennemi pour la transférer au lanceur",
        spellEffect = listOf(
            SpellEffect(target = Target.FRONT, value = -13),
            SpellEffect(target = Target.SELF, value = 7),
        ),
    ),
    Spell(
        tier = SpellTier.TIER_2,
        cost = SpellCost(waterCost = 8, fireCost = 8),
        name = "Nuage de corruption",
        description = "Un nuage toxique et brûlant s'abat sur tous les ennemis",
        spellEffect = listOf(SpellEffect(target = Target.ALL, value = -9)),
    ),
    Spell(
        tier = SpellTier.TIER_3,
        cost = SpellCost(waterCost = 14, fireCost = 14),
        name = "Abîme",
        description = "Un gouffre obscur s'ouvre sous tous les ennemis",
        spellEffect = listOf(SpellEffect(target = Target.ALL, value = -15)),
    ),
    Spell(
        tier = SpellTier.TIER_3,
        cost = SpellCost(waterCost = 14, fireCost = 14),
        name = "Pacte de sang",
        description = "Sacrifie sa propre chair pour déchaîner une onde sombre dévastatrice",
        spellEffect = listOf(
            SpellEffect(target = Target.ALL, value = -22),
            SpellEffect(target = Target.SELF, value = -10),
        ),
    ),
)
