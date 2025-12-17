# KweekBook Logo

## Comment ajouter votre logo personnalisé

Pour remplacer le logo vectoriel par votre propre image :

### Méthode 1 : Image unique (simple)
1. Placez votre fichier image (PNG ou JPG) dans : 
   ```
   app/src/main/res/drawable/
   ```
2. Nommez-le **`logo_kweekbook.png`**
3. L'application utilisera automatiquement votre image !

### Méthode 2 : Multiples résolutions (recommandé)
Pour une meilleure qualité sur tous les appareils, créez plusieurs versions :

```
app/src/main/res/drawable-mdpi/logo_kweekbook.png      (48x48 pixels)
app/src/main/res/drawable-hdpi/logo_kweekbook.png      (72x72 pixels)
app/src/main/res/drawable-xhdpi/logo_kweekbook.png     (96x96 pixels)
app/src/main/res/drawable-xxhdpi/logo_kweekbook.png    (144x144 pixels)
app/src/main/res/drawable-xxxhdpi/logo_kweekbook.png   (192x192 pixels)
```

### Le logo est utilisé dans :
- **Page d'accueil** (HomePageActivity) : Grand logo avec animation bounce
- **Écran de connexion** (LoginActivity) : Logo en haut du formulaire

### Note importante :
- Format recommandé : **PNG avec fond transparent**
- Taille recommandée : **192x192 pixels** minimum
- Le logo actuel est un dessin vectoriel XML, votre image PNG le remplacera automatiquement

---

## Emplacement actuel du logo
📍 **app/src/main/res/drawable/logo_kweekbook.xml** (fichier vectoriel)

Remplacez-le par votre image PNG du même nom pour voir votre logo !
