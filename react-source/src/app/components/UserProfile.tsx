import { ArrowLeft, User, Mail, Heart, BookOpen, Calendar, LogOut } from 'lucide-react';
import { Book } from '../data/books';
import { Button } from './ui/button';
import { Card, CardContent } from './ui/card';
import { Footer } from './Footer';

interface UserProfileProps {
  user: { name: string; email: string };
  favorites: number[];
  books: Book[];
  onBack: () => void;
  onLogout: () => void;
  onSelectBook: (book: Book) => void;
}

export function UserProfile({ user, favorites, books, onBack, onLogout, onSelectBook }: UserProfileProps) {
  const favoriteBooks = books.filter(book => favorites.includes(book.id));
  const memberSince = new Date().toLocaleDateString('fr-FR', { year: 'numeric', month: 'long' });

  return (
    <div className="min-h-screen bg-background">
      {/* Header */}
      <div className="sticky top-0 z-10 bg-background/80 backdrop-blur-lg border-b border-border">
        <div className="max-w-7xl mx-auto px-4 py-4 sm:px-6 lg:px-8">
          <div className="flex items-center justify-between">
            <Button
              onClick={onBack}
              variant="ghost"
              className="gap-2"
            >
              <ArrowLeft className="h-5 w-5" />
              Retour
            </Button>
            <Button
              onClick={onLogout}
              variant="outline"
              className="gap-2 text-destructive hover:text-destructive"
            >
              <LogOut className="h-5 w-5" />
              Déconnexion
            </Button>
          </div>
        </div>
      </div>

      {/* Content */}
      <div className="max-w-7xl mx-auto px-4 py-8 sm:px-6 lg:px-8">
        {/* Profile Header */}
        <Card className="mb-8">
          <CardContent className="p-6">
            <div className="flex flex-col sm:flex-row items-start sm:items-center gap-6">
              <div className="relative">
                <div className="w-24 h-24 rounded-full bg-gradient-to-br from-primary to-accent flex items-center justify-center">
                  <User className="w-12 h-12 text-white" />
                </div>
                <div className="absolute -bottom-1 -right-1 w-6 h-6 bg-green-500 rounded-full border-4 border-background"></div>
              </div>
              
              <div className="flex-1 space-y-3">
                <div>
                  <h1 className="text-3xl">{user.name}</h1>
                  <div className="flex items-center gap-2 text-muted-foreground mt-1">
                    <Mail className="h-4 w-4" />
                    <span>{user.email}</span>
                  </div>
                </div>
                
                <div className="flex flex-wrap gap-4">
                  <div className="flex items-center gap-2">
                    <Calendar className="h-4 w-4 text-primary" />
                    <span className="text-sm">Membre depuis {memberSince}</span>
                  </div>
                </div>
              </div>
            </div>
          </CardContent>
        </Card>

        {/* Stats */}
        <div className="grid grid-cols-1 sm:grid-cols-3 gap-4 mb-8">
          <Card className="border-primary/20 bg-primary/5">
            <CardContent className="p-6 text-center">
              <BookOpen className="w-8 h-8 mx-auto mb-2 text-primary" />
              <div className="text-2xl mb-1 text-primary">{books.length}</div>
              <p className="text-sm text-muted-foreground">Livres disponibles</p>
            </CardContent>
          </Card>
          
          <Card className="border-accent/20 bg-accent/5">
            <CardContent className="p-6 text-center">
              <Heart className="w-8 h-8 mx-auto mb-2 text-accent" />
              <div className="text-2xl mb-1 text-accent">{favorites.length}</div>
              <p className="text-sm text-muted-foreground">Favoris</p>
            </CardContent>
          </Card>
          
          <Card className="border-accent/20 bg-gradient-to-br from-accent/5 to-primary/5">
            <CardContent className="p-6 text-center">
              <BookOpen className="w-8 h-8 mx-auto mb-2 text-accent" />
              <div className="text-2xl mb-1 text-accent">0</div>
              <p className="text-sm text-muted-foreground">Livres empruntés</p>
            </CardContent>
          </Card>
        </div>

        {/* Favorite Books */}
        <div>
          <h2 className="text-2xl mb-4">Mes livres favoris</h2>
          
          {favoriteBooks.length === 0 ? (
            <Card>
              <CardContent className="p-12 text-center">
                <Heart className="w-16 h-16 mx-auto mb-4 text-muted-foreground" />
                <p className="text-muted-foreground">
                  Vous n'avez pas encore de livres favoris.
                </p>
                <Button
                  onClick={onBack}
                  className="mt-4 bg-primary hover:bg-primary/90 text-primary-foreground"
                >
                  Découvrir des livres
                </Button>
              </CardContent>
            </Card>
          ) : (
            <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-6">
              {favoriteBooks.map((book) => (
                <Card 
                  key={book.id} 
                  className="group cursor-pointer hover:shadow-xl transition-all duration-300 overflow-hidden hover:scale-[1.02]"
                  onClick={() => onSelectBook(book)}
                >
                  <CardContent className="p-0">
                    <div className="relative aspect-[3/4] overflow-hidden bg-secondary">
                      <img
                        src={book.image}
                        alt={book.title}
                        className="w-full h-full object-cover group-hover:scale-110 transition-transform duration-300"
                      />
                      <div className="absolute top-3 right-3 p-2 bg-background/80 backdrop-blur-sm rounded-full">
                        <Heart className="h-5 w-5 fill-accent text-accent" />
                      </div>
                    </div>
                    <div className="p-4 space-y-2">
                      <h3 className="line-clamp-1">{book.title}</h3>
                      <p className="text-muted-foreground">{book.author}</p>
                      <span className="text-xs px-2 py-1 bg-accent/10 text-accent rounded-full border border-accent/20 inline-block">
                        {book.genre}
                      </span>
                    </div>
                  </CardContent>
                </Card>
              ))}
            </div>
          )}
        </div>
      </div>
      
      <Footer />
    </div>
  );
}