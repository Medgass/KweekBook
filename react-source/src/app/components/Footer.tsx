import { Heart, Mail, Github, Linkedin, Twitter } from 'lucide-react';
import logo from 'figma:asset/c7a0325c9d08e8026b78e7ce8f58f07a64c11072.png';

export function Footer() {
  const currentYear = new Date().getFullYear();

  return (
    <footer className="bg-card border-t border-border mt-auto">
      <div className="max-w-7xl mx-auto px-4 py-12 sm:px-6 lg:px-8">
        <div className="grid grid-cols-1 md:grid-cols-4 gap-8">
          {/* Logo & Description */}
          <div className="space-y-4">
            <div className="flex items-center gap-2">
              <img src={logo} alt="KweekBook" className="w-10 h-10 object-contain" />
              <span className="text-xl bg-gradient-to-r from-primary to-accent bg-clip-text text-transparent">KweekBook</span>
            </div>
            <p className="text-sm text-muted-foreground">
              Découvrez et gérez votre collection de livres préférés dans une interface élégante et moderne.
            </p>
          </div>

          {/* Navigation */}
          <div>
            <h3 className="mb-4 text-accent">Navigation</h3>
            <ul className="space-y-2 text-sm text-muted-foreground">
              <li>
                <a href="#" className="hover:text-accent transition-colors">
                  Accueil
                </a>
              </li>
              <li>
                <a href="#" className="hover:text-accent transition-colors">
                  Catalogue
                </a>
              </li>
              <li>
                <a href="#" className="hover:text-accent transition-colors">
                  Favoris
                </a>
              </li>
              <li>
                <a href="#" className="hover:text-accent transition-colors">
                  Mon profil
                </a>
              </li>
            </ul>
          </div>

          {/* Resources */}
          <div>
            <h3 className="mb-4 text-primary">Ressources</h3>
            <ul className="space-y-2 text-sm text-muted-foreground">
              <li>
                <a href="#" className="hover:text-primary transition-colors">
                  À propos
                </a>
              </li>
              <li>
                <a href="#" className="hover:text-primary transition-colors">
                  Aide & Support
                </a>
              </li>
              <li>
                <a href="#" className="hover:text-primary transition-colors">
                  Politique de confidentialité
                </a>
              </li>
              <li>
                <a href="#" className="hover:text-primary transition-colors">
                  Conditions d'utilisation
                </a>
              </li>
            </ul>
          </div>

          {/* Contact & Social */}
          <div>
            <h3 className="mb-4 text-accent">Contact</h3>
            <div className="space-y-3 mb-4">
              <a 
                href="mailto:contact@kweekbook.fr" 
                className="flex items-center gap-2 text-sm text-muted-foreground hover:text-accent transition-colors"
              >
                <Mail className="h-4 w-4" />
                contact@kweekbook.fr
              </a>
            </div>
            <div className="flex gap-3">
              <a
                href="#"
                className="p-2 rounded-lg bg-secondary hover:bg-accent/20 hover:text-accent transition-all"
                aria-label="Twitter"
              >
                <Twitter className="h-5 w-5" />
              </a>
              <a
                href="#"
                className="p-2 rounded-lg bg-secondary hover:bg-primary/20 hover:text-primary transition-all"
                aria-label="Github"
              >
                <Github className="h-5 w-5" />
              </a>
              <a
                href="#"
                className="p-2 rounded-lg bg-secondary hover:bg-accent/20 hover:text-accent transition-all"
                aria-label="LinkedIn"
              >
                <Linkedin className="h-5 w-5" />
              </a>
            </div>
          </div>
        </div>

        {/* Bottom */}
        <div className="border-t border-border mt-8 pt-8 flex flex-col sm:flex-row justify-between items-center gap-4">
          <p className="text-sm text-muted-foreground">
            © {currentYear} KweekBook. Tous droits réservés.
          </p>
          <p className="text-sm text-muted-foreground flex items-center gap-1">
            Fait avec <Heart className="h-4 w-4 fill-accent text-accent" /> pour les amoureux des livres
          </p>
        </div>
      </div>
    </footer>
  );
}