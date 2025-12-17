import { ArrowLeft, Heart, Calendar, BookOpen, Hash, Star, Globe, Building2, CheckCircle, XCircle, Clock, User, CalendarDays, Package, History } from 'lucide-react';
import { Book } from '../data/books';
import { Button } from './ui/button';
import { Card, CardContent } from './ui/card';
import { Footer } from './Footer';

interface BookDetailProps {
  book: Book;
  isFavorite: boolean;
  onToggleFavorite: (bookId: number) => void;
  onBack: () => void;
}

export function BookDetail({ book, isFavorite, onToggleFavorite, onBack }: BookDetailProps) {
  const getStatusIcon = () => {
    switch (book.status) {
      case 'available':
        return <CheckCircle className="h-5 w-5 text-primary" />;
      case 'borrowed':
        return <XCircle className="h-5 w-5 text-accent" />;
      case 'reserved':
        return <Clock className="h-5 w-5 text-muted-foreground" />;
    }
  };

  const getStatusText = () => {
    switch (book.status) {
      case 'available':
        return 'Disponible';
      case 'borrowed':
        return 'Emprunté';
      case 'reserved':
        return 'Réservé';
    }
  };

  return (
    <div className="min-h-screen bg-background flex flex-col">
      {/* Header */}
      <div className="sticky top-0 z-10 bg-background/80 backdrop-blur-lg border-b border-border">
        <div className="max-w-7xl mx-auto px-4 py-4 sm:px-6 lg:px-8">
          <Button
            onClick={onBack}
            variant="ghost"
            className="gap-2"
          >
            <ArrowLeft className="h-5 w-5" />
            Retour
          </Button>
        </div>
      </div>

      {/* Content */}
      <div className="max-w-7xl mx-auto px-4 py-8 sm:px-6 lg:px-8 flex-1">
        <div className="grid grid-cols-1 lg:grid-cols-2 gap-8">
          {/* Image */}
          <div className="relative">
            <Card className="overflow-hidden">
              <CardContent className="p-0">
                <div className="aspect-[3/4] relative">
                  <img
                    src={book.image}
                    alt={book.title}
                    className="w-full h-full object-cover"
                  />
                  <div className="absolute inset-0 bg-gradient-to-t from-black/60 via-transparent to-transparent" />
                  <div className="absolute bottom-4 left-4 flex items-center gap-2 bg-background/80 backdrop-blur-sm px-3 py-2 rounded-full">
                    <Star className="h-5 w-5 fill-accent text-accent" />
                    <span className="text-lg">{book.rating} / 5</span>
                  </div>
                </div>
              </CardContent>
            </Card>
          </div>

          {/* Details */}
          <div className="space-y-6">
            <div>
              <div className="flex items-start justify-between gap-4 mb-4">
                <div className="flex-1">
                  <h1 className="text-4xl mb-2">{book.title}</h1>
                  <p className="text-xl text-muted-foreground">{book.author}</p>
                </div>
                <Button
                  onClick={() => onToggleFavorite(book.id)}
                  variant="outline"
                  size="icon"
                  className="rounded-full h-12 w-12 flex-shrink-0"
                >
                  <Heart
                    className={`h-6 w-6 transition-colors ${
                      isFavorite
                        ? 'fill-accent text-accent'
                        : 'text-foreground'
                    }`}
                  />
                </Button>
              </div>
              
              <div className="flex items-center gap-2 flex-wrap">
                <span className="inline-flex items-center gap-2 px-3 py-1.5 bg-accent/10 text-accent rounded-full border border-accent/20">
                  {book.genre}
                </span>
                <span className="inline-flex items-center gap-2 px-3 py-1.5 bg-primary/10 text-primary rounded-full border border-primary/20">
                  {book.category}
                </span>
              </div>
            </div>

            {/* Status */}
            <Card>
              <CardContent className="p-6">
                <div className="flex items-center gap-3 mb-4">
                  {getStatusIcon()}
                  <div>
                    <p className="text-sm text-muted-foreground">Statut</p>
                    <p className="text-lg">{getStatusText()}</p>
                  </div>
                </div>
                
                {/* Informations d'emprunt pour les livres empruntés */}
                {book.status === 'borrowed' && book.borrowedBy && (
                  <div className="mt-4 pt-4 border-t border-border space-y-3">
                    <div className="flex items-center gap-3">
                      <div className="p-2 bg-accent/10 rounded-lg">
                        <User className="h-4 w-4 text-accent" />
                      </div>
                      <div>
                        <p className="text-sm text-muted-foreground">Emprunté par</p>
                        <p className="text-sm">{book.borrowedBy}</p>
                      </div>
                    </div>
                    <div className="flex items-center gap-3">
                      <div className="p-2 bg-accent/10 rounded-lg">
                        <CalendarDays className="h-4 w-4 text-accent" />
                      </div>
                      <div>
                        <p className="text-sm text-muted-foreground">Date d'emprunt</p>
                        <p className="text-sm">{new Date(book.borrowDate!).toLocaleDateString('fr-FR')}</p>
                      </div>
                    </div>
                    <div className="flex items-center gap-3">
                      <div className="p-2 bg-accent/10 rounded-lg">
                        <CalendarDays className="h-4 w-4 text-accent" />
                      </div>
                      <div>
                        <p className="text-sm text-muted-foreground">Date de retour prévue</p>
                        <p className="text-sm">{new Date(book.returnDate!).toLocaleDateString('fr-FR')}</p>
                      </div>
                    </div>
                  </div>
                )}
                
                {/* Informations de réservation */}
                {book.status === 'reserved' && book.reservedBy && (
                  <div className="mt-4 pt-4 border-t border-border space-y-3">
                    <div className="flex items-center gap-3">
                      <div className="p-2 bg-accent/10 rounded-lg">
                        <User className="h-4 w-4 text-accent" />
                      </div>
                      <div>
                        <p className="text-sm text-muted-foreground">Réservé par</p>
                        <p className="text-sm">{book.reservedBy}</p>
                      </div>
                    </div>
                    <div className="flex items-center gap-3">
                      <div className="p-2 bg-accent/10 rounded-lg">
                        <CalendarDays className="h-4 w-4 text-accent" />
                      </div>
                      <div>
                        <p className="text-sm text-muted-foreground">Date de réservation</p>
                        <p className="text-sm">{new Date(book.reservedDate!).toLocaleDateString('fr-FR')}</p>
                      </div>
                    </div>
                  </div>
                )}
              </CardContent>
            </Card>

            {/* Disponibilité des exemplaires */}
            <Card className="border-accent/20 bg-accent/5">
              <CardContent className="p-6">
                <div className="flex items-center gap-3 mb-3">
                  <div className="p-2 bg-accent/10 rounded-lg">
                    <Package className="h-5 w-5 text-accent" />
                  </div>
                  <h2 className="text-accent">Disponibilité</h2>
                </div>
                <div className="space-y-2">
                  <div className="flex justify-between">
                    <span className="text-muted-foreground">Exemplaires disponibles</span>
                    <span className="text-accent font-medium">{book.copies.available} / {book.copies.total}</span>
                  </div>
                  <div className="flex justify-between">
                    <span className="text-muted-foreground">Durée d'emprunt maximale</span>
                    <span className="text-accent font-medium">{book.maxBorrowDays} jours</span>
                  </div>
                </div>
                <div className="w-full bg-secondary rounded-full h-2 mt-4">
                  <div 
                    className="bg-accent h-2 rounded-full transition-all duration-300" 
                    style={{ width: `${(book.copies.available / book.copies.total) * 100}%` }}
                  />
                </div>
              </CardContent>
            </Card>

            {/* Historique d'emprunt */}
            {book.borrowHistory.length > 0 && (
              <Card>
                <CardContent className="p-6">
                  <div className="flex items-center gap-3 mb-4">
                    <div className="p-2 bg-primary/10 rounded-lg">
                      <History className="h-5 w-5 text-primary" />
                    </div>
                    <h2>Historique d'emprunt</h2>
                  </div>
                  <div className="space-y-3">
                    {book.borrowHistory.map((history, index) => (
                      <div 
                        key={index} 
                        className="p-4 bg-secondary rounded-lg border border-border"
                      >
                        <div className="flex justify-between items-start">
                          <div className="space-y-1">
                            <p className="font-medium">{history.borrower}</p>
                            <p className="text-sm text-muted-foreground">
                              Du {new Date(history.borrowDate).toLocaleDateString('fr-FR')} au {new Date(history.returnDate).toLocaleDateString('fr-FR')}
                            </p>
                          </div>
                          {history.returned && (
                            <span className="text-xs px-2 py-1 bg-primary/10 text-primary rounded-full border border-primary/20">
                              Retourné
                            </span>
                          )}
                        </div>
                      </div>
                    ))}
                  </div>
                </CardContent>
              </Card>
            )}

            {/* Description */}
            <Card>
              <CardContent className="p-6">
                <h2 className="mb-3">Synopsis</h2>
                <p className="text-muted-foreground leading-relaxed">
                  {book.description}
                </p>
              </CardContent>
            </Card>

            {/* Information */}
            <Card>
              <CardContent className="p-6 space-y-4">
                <h2 className="mb-3">Informations</h2>
                
                <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
                  <div className="flex items-center gap-3">
                    <div className="p-2 bg-primary/10 rounded-lg">
                      <Calendar className="h-5 w-5 text-primary" />
                    </div>
                    <div>
                      <p className="text-sm text-muted-foreground">Année</p>
                      <p>{book.year}</p>
                    </div>
                  </div>

                  <div className="flex items-center gap-3">
                    <div className="p-2 bg-primary/10 rounded-lg">
                      <BookOpen className="h-5 w-5 text-primary" />
                    </div>
                    <div>
                      <p className="text-sm text-muted-foreground">Pages</p>
                      <p>{book.pages}</p>
                    </div>
                  </div>

                  <div className="flex items-center gap-3">
                    <div className="p-2 bg-accent/10 rounded-lg">
                      <Globe className="h-5 w-5 text-accent" />
                    </div>
                    <div>
                      <p className="text-sm text-muted-foreground">Langue</p>
                      <p>{book.language}</p>
                    </div>
                  </div>

                  <div className="flex items-center gap-3">
                    <div className="p-2 bg-accent/10 rounded-lg">
                      <Building2 className="h-5 w-5 text-accent" />
                    </div>
                    <div>
                      <p className="text-sm text-muted-foreground">Éditeur</p>
                      <p>{book.publisher}</p>
                    </div>
                  </div>

                  <div className="flex items-center gap-3 sm:col-span-2">
                    <div className="p-2 bg-primary/10 rounded-lg">
                      <Hash className="h-5 w-5 text-primary" />
                    </div>
                    <div>
                      <p className="text-sm text-muted-foreground">ISBN</p>
                      <p>{book.isbn}</p>
                    </div>
                  </div>
                </div>
              </CardContent>
            </Card>

            {/* Actions */}
            <div className="flex gap-4">
              <Button 
                className="flex-1 bg-primary hover:bg-primary/90 text-primary-foreground"
                disabled={book.status !== 'available'}
              >
                {book.status === 'available' ? 'Emprunter ce livre' : 'Non disponible'}
              </Button>
              <Button 
                variant="outline"
                className="flex-1"
              >
                Partager
              </Button>
            </div>
          </div>
        </div>
      </div>
      
      <Footer />
    </div>
  );
}