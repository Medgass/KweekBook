import { useState, useMemo } from 'react';
import { Search, Heart, Moon, Sun, User, LogOut, Menu, Filter, Star } from 'lucide-react';
import { Book } from '../data/books';
import { Input } from './ui/input';
import { Card, CardContent } from './ui/card';
import { Button } from './ui/button';
import { Footer } from './Footer';
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuSeparator,
  DropdownMenuTrigger,
} from './ui/dropdown-menu';
import logo from 'figma:asset/c7a0325c9d08e8026b78e7ce8f58f07a64c11072.png';

interface BookListProps {
  books: Book[];
  favorites: number[];
  onToggleFavorite: (bookId: number) => void;
  onSelectBook: (book: Book) => void;
  isDarkMode: boolean;
  onToggleDarkMode: () => void;
  onProfileClick: () => void;
  onLogout: () => void;
  userName: string;
}

export function BookList({ 
  books, 
  favorites, 
  onToggleFavorite, 
  onSelectBook,
  isDarkMode,
  onToggleDarkMode,
  onProfileClick,
  onLogout,
  userName
}: BookListProps) {
  const [searchQuery, setSearchQuery] = useState('');
  const [selectedCategory, setSelectedCategory] = useState<string>('Tous');

  // Get unique categories
  const categories = useMemo(() => {
    const cats = ['Tous', ...Array.from(new Set(books.map(book => book.category)))];
    return cats;
  }, [books]);

  const filteredBooks = useMemo(() => {
    let filtered = books;
    
    // Filter by category
    if (selectedCategory !== 'Tous') {
      filtered = filtered.filter(book => book.category === selectedCategory);
    }
    
    // Filter by search query
    if (searchQuery.trim()) {
      const query = searchQuery.toLowerCase();
      filtered = filtered.filter(book => 
        book.title.toLowerCase().includes(query) ||
        book.author.toLowerCase().includes(query) ||
        book.genre.toLowerCase().includes(query) ||
        book.category.toLowerCase().includes(query)
      );
    }
    
    return filtered;
  }, [books, searchQuery, selectedCategory]);

  const getStatusBadge = (status: string) => {
    switch (status) {
      case 'available':
        return <span className="text-xs px-2 py-1 bg-primary/10 text-primary rounded-full border border-primary/20">Disponible</span>;
      case 'borrowed':
        return <span className="text-xs px-2 py-1 bg-accent/10 text-accent rounded-full border border-accent/20">Emprunté</span>;
      case 'reserved':
        return <span className="text-xs px-2 py-1 bg-muted text-muted-foreground rounded-full border border-border">Réservé</span>;
      default:
        return null;
    }
  };

  return (
    <div className="min-h-screen bg-background flex flex-col">
      {/* Header */}
      <div className="sticky top-0 z-10 bg-background/80 backdrop-blur-lg border-b border-border">
        <div className="max-w-7xl mx-auto px-4 py-4 sm:px-6 lg:px-8">
          <div className="flex items-center justify-between gap-4 flex-wrap">
            <div className="flex items-center gap-3">
              <img src={logo} alt="KweekBook" className="w-12 h-12 object-contain" />
              <h1 className="text-3xl">KweekBook</h1>
            </div>
            <div className="flex items-center gap-2">
              <DropdownMenu>
                <DropdownMenuTrigger asChild>
                  <Button
                    variant="outline"
                    size="icon"
                    className="rounded-full"
                  >
                    <Menu className="h-5 w-5" />
                  </Button>
                </DropdownMenuTrigger>
                <DropdownMenuContent align="end" className="w-56">
                  <div className="px-2 py-2">
                    <p className="text-sm font-medium">{userName}</p>
                    <p className="text-xs text-muted-foreground">Utilisateur connecté</p>
                  </div>
                  <DropdownMenuSeparator />
                  <DropdownMenuItem onClick={onProfileClick} className="cursor-pointer">
                    <User className="mr-2 h-4 w-4" />
                    <span>Mon profil</span>
                  </DropdownMenuItem>
                  <DropdownMenuItem onClick={onToggleDarkMode} className="cursor-pointer">
                    {isDarkMode ? <Sun className="mr-2 h-4 w-4" /> : <Moon className="mr-2 h-4 w-4" />}
                    <span>{isDarkMode ? 'Mode clair' : 'Mode sombre'}</span>
                  </DropdownMenuItem>
                  <DropdownMenuSeparator />
                  <DropdownMenuItem 
                    onClick={onLogout} 
                    className="cursor-pointer text-destructive focus:text-destructive"
                  >
                    <LogOut className="mr-2 h-4 w-4" />
                    <span>Déconnexion</span>
                  </DropdownMenuItem>
                </DropdownMenuContent>
              </DropdownMenu>
            </div>
          </div>
          
          {/* Search Bar */}
          <div className="mt-4 relative">
            <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 text-accent h-5 w-5" />
            <Input
              type="text"
              placeholder="Rechercher un livre, auteur ou genre..."
              value={searchQuery}
              onChange={(e) => setSearchQuery(e.target.value)}
              className="pl-10 bg-secondary focus:border-accent focus:ring-accent"
            />
          </div>

          {/* Category Filter */}
          <div className="mt-4 flex items-center gap-2 overflow-x-auto pb-2">
            <Filter className="h-5 w-5 text-accent flex-shrink-0" />
            {categories.map((category) => (
              <button
                key={category}
                onClick={() => setSelectedCategory(category)}
                className={`px-4 py-2 rounded-full text-sm whitespace-nowrap transition-all ${
                  selectedCategory === category
                    ? 'bg-accent text-accent-foreground shadow-lg shadow-accent/30'
                    : 'bg-secondary text-foreground hover:bg-accent/20 hover:text-accent border border-border'
                }`}
              >
                {category}
              </button>
            ))}
          </div>
        </div>
      </div>

      {/* Books Grid */}
      <div className="max-w-7xl mx-auto px-4 py-8 sm:px-6 lg:px-8 flex-1">
        {filteredBooks.length === 0 ? (
          <div className="text-center py-16">
            <p className="text-muted-foreground">Aucun livre trouvé</p>
          </div>
        ) : (
          <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-6">
            {filteredBooks.map((book) => (
              <Card 
                key={book.id} 
                className="group cursor-pointer hover:shadow-xl transition-all duration-300 overflow-hidden hover:scale-[1.02]"
              >
                <CardContent className="p-0">
                  <div className="relative aspect-[3/4] overflow-hidden bg-secondary">
                    <img
                      src={book.image}
                      alt={book.title}
                      className="w-full h-full object-cover group-hover:scale-110 transition-transform duration-300"
                      onClick={() => onSelectBook(book)}
                    />
                    <button
                      onClick={(e) => {
                        e.stopPropagation();
                        onToggleFavorite(book.id);
                      }}
                      className="absolute top-3 right-3 p-2 bg-background/80 backdrop-blur-sm rounded-full hover:bg-background transition-all"
                    >
                      <Heart
                        className={`h-5 w-5 transition-colors ${
                          favorites.includes(book.id)
                            ? 'fill-accent text-accent'
                            : 'text-foreground'
                        }`}
                      />
                    </button>
                    <div className="absolute bottom-3 left-3 flex items-center gap-1 bg-background/80 backdrop-blur-sm px-2 py-1 rounded-full">
                      <Star className="h-4 w-4 fill-accent text-accent" />
                      <span className="text-sm">{book.rating}</span>
                    </div>
                  </div>
                  <div 
                    className="p-4 space-y-2"
                    onClick={() => onSelectBook(book)}
                  >
                    <h3 className="line-clamp-1">{book.title}</h3>
                    <p className="text-muted-foreground">{book.author}</p>
                    <div className="flex items-center gap-2 flex-wrap">
                      <span className="text-xs px-2 py-1 bg-accent/10 text-accent rounded-full border border-accent/20">
                        {book.genre}
                      </span>
                      {getStatusBadge(book.status)}
                    </div>
                  </div>
                </CardContent>
              </Card>
            ))}
          </div>
        )}
      </div>
      
      <Footer />
    </div>
  );
}