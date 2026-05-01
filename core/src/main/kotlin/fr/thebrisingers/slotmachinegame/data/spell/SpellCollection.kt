package fr.thebrisingers.slotmachinegame.data.spell

val spellCollection = listOf(

    // ─────────────────────────────────────────────
    // 🔥 FEU
    // ─────────────────────────────────────────────

    Spell(
        cost = SpellCost(fireCost = 2),
        name = "Étincelle",
        description = "Inflige de légers dégâts au premier ennemi",
        spellEffect = listOf(
            SpellEffect(target = Target.FRONT, value = -3)
        ),
    ),
    Spell(
        cost = SpellCost(fireCost = 4),
        name = "Boule de feu",
        description = "Inflige des dégâts modérés au premier ennemi",
        spellEffect = listOf(
            SpellEffect(target = Target.FRONT, value = -6)
        ),
    ),
    Spell(
        cost = SpellCost(fireCost = 5),
        name = "Souffle ardent",
        description = "Inflige de faibles dégâts à tous les ennemis",
        spellEffect = listOf(
            SpellEffect(target = Target.ALL, value = -3)
        ),
    ),
    Spell(
        cost = SpellCost(fireCost = 7),
        name = "Torche vivante",
        description = "Enflamme le premier ennemi, lui infligeant des dégâts importants",
        spellEffect = listOf(
            SpellEffect(target = Target.FRONT, value = -12)
        ),
    ),
    Spell(
        cost = SpellCost(fireCost = 10),
        name = "Météore",
        description = "Fait s'écraser un rocher enflammé sur tous les ennemis",
        spellEffect = listOf(
            SpellEffect(target = Target.ALL, value = -8)
        ),
    ),
    Spell(
        cost = SpellCost(fireCost = 6),
        name = "Mur de flammes",
        description = "Crée un bouclier de feu qui renvoie des dégâts aux attaquants",
        spellEffect = listOf(
            SpellEffect(target = Target.SELF, value = 5)
        ),
    ),
    Spell(
        cost = SpellCost(fireCost = 3),
        name = "Immolation",
        description = "Inflige des dégâts massifs au premier ennemi, mais coûte aussi des PV au lanceur",
        spellEffect = listOf(
            SpellEffect(target = Target.FRONT, value = -15),
            SpellEffect(target = Target.SELF, value = -5),
        ),
    ),

    // ─────────────────────────────────────────────
    // 💧 EAU
    // ─────────────────────────────────────────────

    Spell(
        cost = SpellCost(waterCost = 2),
        name = "Gouttelette",
        description = "Inflige de légers dégâts au premier ennemi",
        spellEffect = listOf(
            SpellEffect(target = Target.FRONT, value = -3)
        ),
    ),
    Spell(
        cost = SpellCost(waterCost = 4),
        name = "Jet d'eau",
        description = "Inflige des dégâts modérés au premier ennemi",
        spellEffect = listOf(
            SpellEffect(target = Target.FRONT, value = -6)
        ),
    ),
    Spell(
        cost = SpellCost(waterCost = 4),
        name = "Petite vague",
        description = "Inflige de faibles dégâts à tous les ennemis",
        spellEffect = listOf(
            SpellEffect(target = Target.ALL, value = -3)
        ),
    ),
    Spell(
        cost = SpellCost(waterCost = 9),
        name = "Raz-de-marée",
        description = "Vague dévastatrice sur tous les ennemis",
        spellEffect = listOf(
            SpellEffect(target = Target.ALL, value = -7)
        ),
    ),
    Spell(
        cost = SpellCost(waterCost = 8),
        name = "Tourbillon",
        description = "Aspire tous les ennemis et leur inflige des dégâts",
        spellEffect = listOf(
            SpellEffect(target = Target.ALL, value = -6)
        ),
    ),
    Spell(
        cost = SpellCost(waterCost = 5),
        name = "Soin aquatique",
        description = "Restaure des PV au lanceur",
        spellEffect = listOf(
            SpellEffect(target = Target.SELF, value = 8)
        ),
    ),
    Spell(
        cost = SpellCost(waterCost = 4),
        name = "Brume épaisse",
        description = "Réduit la précision de tous les ennemis",
        spellEffect = listOf(
            SpellEffect(target = Target.ALL, value = -2)
        ),
    ),

    // ─────────────────────────────────────────────
    // 🌿 NATURE
    // ─────────────────────────────────────────────

    Spell(
        cost = SpellCost(earthCost = 2),
        name = "Vrille",
        description = "Une liane fouette le premier ennemi",
        spellEffect = listOf(
            SpellEffect(target = Target.FRONT, value = -3)
        ),
    ),
    Spell(
        cost = SpellCost(earthCost = 5),
        name = "Racines enchevêtrées",
        description = "Immobilise et inflige des dégâts au premier ennemi",
        spellEffect = listOf(
            SpellEffect(target = Target.FRONT, value = -7)
        ),
    ),
    Spell(
        cost = SpellCost(earthCost = 5),
        name = "Nuée d'épines",
        description = "Projette une pluie d'épines sur tous les ennemis",
        spellEffect = listOf(
            SpellEffect(target = Target.ALL, value = -4)
        ),
    ),
    Spell(
        cost = SpellCost(earthCost = 4),
        name = "Régénération",
        description = "Restaure progressivement les PV du lanceur",
        spellEffect = listOf(
            SpellEffect(target = Target.SELF, value = 7)
        ),
    ),
    Spell(
        cost = SpellCost(earthCost = 8),
        name = "Forêt hostile",
        description = "Fait surgir des ronces sous tous les ennemis",
        spellEffect = listOf(
            SpellEffect(target = Target.ALL, value = -6)
        ),
    ),
    Spell(
        cost = SpellCost(earthCost = 6),
        name = "Spores toxiques",
        description = "Empoisonne tous les ennemis pour des dégâts sur la durée",
        spellEffect = listOf(
            SpellEffect(target = Target.ALL, value = -5)
        ),
    ),
    Spell(
        cost = SpellCost(earthCost = 7),
        name = "Étreinte sylvestre",
        description = "Des racines surgissent et broient le dernier ennemi",
        spellEffect = listOf(
            SpellEffect(target = Target.BACK, value = -11)
        ),
    ),

    // ─────────────────────────────────────────────
    // 💨 AIR
    // ─────────────────────────────────────────────

    Spell(
        cost = SpellCost(windCost = 2),
        name = "Coup de vent",
        description = "Souffle léger sur le premier ennemi",
        spellEffect = listOf(
            SpellEffect(target = Target.FRONT, value = -3)
        ),
    ),
    Spell(
        cost = SpellCost(windCost = 4),
        name = "Bourrasque",
        description = "Inflige des dégâts modérés au dernier ennemi",
        spellEffect = listOf(
            SpellEffect(target = Target.BACK, value = -6)
        ),
    ),
    Spell(
        cost = SpellCost(windCost = 8),
        name = "Cyclone",
        description = "Tourbillon de vent sur tous les ennemis",
        spellEffect = listOf(
            SpellEffect(target = Target.ALL, value = -6)
        ),
    ),
    Spell(
        cost = SpellCost(windCost = 6),
        name = "Tranchant du vent",
        description = "Lame d'air comprimé sur le premier ennemi, dégâts élevés",
        spellEffect = listOf(
            SpellEffect(target = Target.FRONT, value = -10)
        ),
    ),
    Spell(
        cost = SpellCost(windCost = 4),
        name = "Courant ascendant",
        description = "Réduit les dégâts reçus lors du prochain tour",
        spellEffect = listOf(
            SpellEffect(target = Target.SELF, value = 5)
        ),
    ),
    Spell(
        cost = SpellCost(windCost = 7),
        name = "Vortex",
        description = "Aspire tous les ennemis vers le centre, les étourdissant",
        spellEffect = listOf(
            SpellEffect(target = Target.ALL, value = -5)
        ),
    ),
    Spell(
        cost = SpellCost(windCost = 5),
        name = "Rafale perçante",
        description = "Perce les défenses du premier ennemi",
        spellEffect = listOf(
            SpellEffect(target = Target.FRONT, value = -8)
        ),
    ),

    // ─────────────────────────────────────────────
    // ⚡ FOUDRE (Feu + Air)
    // ─────────────────────────────────────────────

    Spell(
        cost = SpellCost(fireCost = 2, windCost = 1),
        name = "Décharge statique",
        description = "Petite décharge électrique sur le premier ennemi",
        spellEffect = listOf(
            SpellEffect(target = Target.FRONT, value = -4)
        ),
    ),
    Spell(
        cost = SpellCost(fireCost = 3, windCost = 3),
        name = "Éclair",
        description = "Frappe le premier ennemi avec un éclair",
        spellEffect = listOf(
            SpellEffect(target = Target.FRONT, value = -9)
        ),
    ),
    Spell(
        cost = SpellCost(fireCost = 4, windCost = 4),
        name = "Chaîne électrique",
        description = "L'éclair rebondit d'ennemi en ennemi",
        spellEffect = listOf(
            SpellEffect(target = Target.ALL, value = -5)
        ),
    ),
    Spell(
        cost = SpellCost(fireCost = 5, windCost = 5),
        name = "Foudre divine",
        description = "Frappe violemment le premier ennemi depuis les cieux",
        spellEffect = listOf(
            SpellEffect(target = Target.FRONT, value = -16)
        ),
    ),
    Spell(
        cost = SpellCost(fireCost = 6, windCost = 5),
        name = "Tempête électrique",
        description = "Déclenche une tempête qui frappe aléatoirement plusieurs fois",
        spellEffect = listOf(
            SpellEffect(target = Target.ALL, value = -7)
        ),
    ),
    Spell(
        cost = SpellCost(fireCost = 4, windCost = 4),
        name = "Arc galvanique",
        description = "Électrocute le premier et le dernier ennemi simultanément",
        spellEffect = listOf(
            SpellEffect(target = Target.FRONT, value = -8),
            SpellEffect(target = Target.BACK, value = -8),
        ),
    ),
    Spell(
        cost = SpellCost(fireCost = 3, windCost = 2),
        name = "Surcharge",
        description = "Absorbe l'énergie électrique et renvoie des dégâts au prochain attaquant",
        spellEffect = listOf(
            SpellEffect(target = Target.SELF, value = 6)
        ),
    ),

    // ─────────────────────────────────────────────
    // 🌋 MAGMA (Feu + Nature)
    // ─────────────────────────────────────────────

    Spell(
        cost = SpellCost(fireCost = 3, earthCost = 2),
        name = "Crachée de lave",
        description = "Projection de lave brûlante sur le premier ennemi",
        spellEffect = listOf(
            SpellEffect(target = Target.FRONT, value = -8)
        ),
    ),
    Spell(
        cost = SpellCost(fireCost = 4, earthCost = 4),
        name = "Coulée de magma",
        description = "Flux de lave qui brûle tous les ennemis",
        spellEffect = listOf(
            SpellEffect(target = Target.ALL, value = -6)
        ),
    ),
    Spell(
        cost = SpellCost(fireCost = 4, earthCost = 3),
        name = "Soulèvement volcanique",
        description = "Éruption locale sur le premier et le dernier ennemi",
        spellEffect = listOf(
            SpellEffect(target = Target.FRONT, value = -6),
            SpellEffect(target = Target.BACK, value = -6),
        ),
    ),
    Spell(
        cost = SpellCost(fireCost = 5, earthCost = 4),
        name = "Pilier de lave",
        description = "Colonne de magma jaillit sous le premier ennemi",
        spellEffect = listOf(
            SpellEffect(target = Target.FRONT, value = -14)
        ),
    ),
    Spell(
        cost = SpellCost(fireCost = 4, earthCost = 3),
        name = "Cendres toxiques",
        description = "Nuage de cendres qui brûle et empoisonne tous les ennemis",
        spellEffect = listOf(
            SpellEffect(target = Target.ALL, value = -5)
        ),
    ),
    Spell(
        cost = SpellCost(fireCost = 3, earthCost = 3),
        name = "Bouclier de roche fondue",
        description = "Entoure le lanceur d'une carapace de lave protectrice",
        spellEffect = listOf(
            SpellEffect(target = Target.SELF, value = 7)
        ),
    ),

    // ─────────────────────────────────────────────
    // 🧊 GLACE (Eau + Air)
    // ─────────────────────────────────────────────

    Spell(
        cost = SpellCost(waterCost = 2, windCost = 1),
        name = "Givre",
        description = "Fine couche de glace inflige de légers dégâts au premier ennemi",
        spellEffect = listOf(
            SpellEffect(target = Target.FRONT, value = -4)
        ),
    ),
    Spell(
        cost = SpellCost(waterCost = 3, windCost = 2),
        name = "Flèche de glace",
        description = "Projectile acéré de glace sur le premier ennemi",
        spellEffect = listOf(
            SpellEffect(target = Target.FRONT, value = -8)
        ),
    ),
    Spell(
        cost = SpellCost(waterCost = 4, windCost = 3),
        name = "Tempête de grêle",
        description = "Grêle cinglante sur tous les ennemis",
        spellEffect = listOf(
            SpellEffect(target = Target.ALL, value = -5)
        ),
    ),
    Spell(
        cost = SpellCost(waterCost = 5, windCost = 5),
        name = "Blizzard",
        description = "Tempête glaciale dévastatrice sur tous les ennemis",
        spellEffect = listOf(
            SpellEffect(target = Target.ALL, value = -8)
        ),
    ),
    Spell(
        cost = SpellCost(waterCost = 4, windCost = 2),
        name = "Prison de glace",
        description = "Emprisonne le premier ennemi dans un bloc de glace",
        spellEffect = listOf(
            SpellEffect(target = Target.FRONT, value = -10)
        ),
    ),
    Spell(
        cost = SpellCost(waterCost = 4, windCost = 4),
        name = "Souffle arctique",
        description = "Ralentit et inflige des dégâts à tous les ennemis",
        spellEffect = listOf(
            SpellEffect(target = Target.ALL, value = -6)
        ),
    ),
    Spell(
        cost = SpellCost(waterCost = 3, windCost = 2),
        name = "Aiguilles de givre",
        description = "Pluie d'éclats de glace sur le dernier ennemi",
        spellEffect = listOf(
            SpellEffect(target = Target.BACK, value = -8)
        ),
    ),

    // ─────────────────────────────────────────────
    // 🌸 FÉÉRIQUE (Nature + Eau)
    // ─────────────────────────────────────────────

    Spell(
        cost = SpellCost(earthCost = 2, waterCost = 2),
        name = "Poussière de fée",
        description = "Poussière magique qui affaiblit tous les ennemis",
        spellEffect = listOf(
            SpellEffect(target = Target.ALL, value = -3)
        ),
    ),
    Spell(
        cost = SpellCost(earthCost = 3, waterCost = 2),
        name = "Rosée enchantée",
        description = "Restaure des PV et améliore les prochains sorts",
        spellEffect = listOf(
            SpellEffect(target = Target.SELF, value = 9)
        ),
    ),
    Spell(
        cost = SpellCost(earthCost = 3, waterCost = 3),
        name = "Floraison mystique",
        description = "Fleurs magiques qui infligent des dégâts à tous les ennemis",
        spellEffect = listOf(
            SpellEffect(target = Target.ALL, value = -5)
        ),
    ),
    Spell(
        cost = SpellCost(earthCost = 4, waterCost = 3),
        name = "Illusion aquatique",
        description = "Crée des mirages qui confondent et blessent les ennemis",
        spellEffect = listOf(
            SpellEffect(target = Target.ALL, value = -5)
        ),
    ),
    Spell(
        cost = SpellCost(earthCost = 4, waterCost = 4),
        name = "Chant des nymphes",
        description = "Mélodie envoûtante qui paralyse tous les ennemis",
        spellEffect = listOf(
            SpellEffect(target = Target.ALL, value = -6)
        ),
    ),
    Spell(
        cost = SpellCost(earthCost = 3, waterCost = 3),
        name = "Métamorphose",
        description = "Transforme brièvement le premier ennemi, réduisant ses capacités",
        spellEffect = listOf(
            SpellEffect(target = Target.FRONT, value = -7)
        ),
    ),
    Spell(
        cost = SpellCost(earthCost = 5, waterCost = 4),
        name = "Pluie de pétales tranchants",
        description = "Pétales enchantés qui lacèrent tous les ennemis",
        spellEffect = listOf(
            SpellEffect(target = Target.ALL, value = -7)
        ),
    ),

    // ─────────────────────────────────────────────
    // ☀️ LUMIÈRE (Air + Nature)
    // ─────────────────────────────────────────────

    Spell(
        cost = SpellCost(windCost = 2, earthCost = 1),
        name = "Rayon de lumière",
        description = "Fin rayon lumineux sur le premier ennemi",
        spellEffect = listOf(
            SpellEffect(target = Target.FRONT, value = -4)
        ),
    ),
    Spell(
        cost = SpellCost(windCost = 3, earthCost = 3),
        name = "Éclair radieux",
        description = "Rayon éblouissant sur le premier ennemi",
        spellEffect = listOf(
            SpellEffect(target = Target.FRONT, value = -9)
        ),
    ),
    Spell(
        cost = SpellCost(windCost = 5, earthCost = 4),
        name = "Nova solaire",
        description = "Explosion de lumière aveuglante sur tous les ennemis",
        spellEffect = listOf(
            SpellEffect(target = Target.ALL, value = -7)
        ),
    ),
    Spell(
        cost = SpellCost(windCost = 3, earthCost = 2),
        name = "Bénédiction",
        description = "Augmente les dégâts des prochains sorts",
        spellEffect = listOf(
            SpellEffect(target = Target.SELF, value = 6)
        ),
    ),
    Spell(
        cost = SpellCost(windCost = 5, earthCost = 5),
        name = "Jugement céleste",
        description = "Rayon de lumière divine sur le premier ennemi",
        spellEffect = listOf(
            SpellEffect(target = Target.FRONT, value = -16)
        ),
    ),
    Spell(
        cost = SpellCost(windCost = 3, earthCost = 2),
        name = "Voile de lumière",
        description = "Réduit la puissance d'attaque de tous les ennemis",
        spellEffect = listOf(
            SpellEffect(target = Target.ALL, value = -4)
        ),
    ),
    Spell(
        cost = SpellCost(windCost = 4, earthCost = 3),
        name = "Aurore",
        description = "Lumière apaisante qui soigne le lanceur et brûle les ennemis",
        spellEffect = listOf(
            SpellEffect(target = Target.ALL, value = -5),
            SpellEffect(target = Target.SELF, value = 4),
        ),
    ),

    // ─────────────────────────────────────────────
    // 🌑 SOMBRE (Eau + Feu)
    // ─────────────────────────────────────────────

    Spell(
        cost = SpellCost(waterCost = 2, fireCost = 1),
        name = "Tendrilles obscures",
        description = "Filaments de ténèbres qui griffent le premier ennemi",
        spellEffect = listOf(
            SpellEffect(target = Target.FRONT, value = -4)
        ),
    ),
    Spell(
        cost = SpellCost(waterCost = 3, fireCost = 3),
        name = "Drain vital",
        description = "Absorbe les PV du premier ennemi pour les transférer au lanceur",
        spellEffect = listOf(
            SpellEffect(target = Target.FRONT, value = -7),
            SpellEffect(target = Target.SELF, value = 4),
        ),
    ),
    Spell(
        cost = SpellCost(waterCost = 4, fireCost = 3),
        name = "Nuage de corruption",
        description = "Nuage toxique et brûlant sur tous les ennemis",
        spellEffect = listOf(
            SpellEffect(target = Target.ALL, value = -5)
        ),
    ),
    Spell(
        cost = SpellCost(waterCost = 3, fireCost = 2),
        name = "Malédiction",
        description = "Affaiblit durablement le premier ennemi",
        spellEffect = listOf(
            SpellEffect(target = Target.FRONT, value = -6)
        ),
    ),
    Spell(
        cost = SpellCost(waterCost = 5, fireCost = 5),
        name = "Abîme",
        description = "Ouvre un gouffre obscur sous tous les ennemis",
        spellEffect = listOf(
            SpellEffect(target = Target.ALL, value = -8)
        ),
    ),
    Spell(
        cost = SpellCost(waterCost = 1, fireCost = 2),
        name = "Pacte de sang",
        description = "Sacrifie des PV pour décupler la puissance du prochain sort",
        spellEffect = listOf(
            SpellEffect(target = Target.SELF, value = -4)
        ),
    ),
    Spell(
        cost = SpellCost(waterCost = 4, fireCost = 4),
        name = "Vague de désespoir",
        description = "Onde noire qui frappe tous les ennemis et réduit leur moral",
        spellEffect = listOf(
            SpellEffect(target = Target.ALL, value = -6)
        ),
    ),
)
