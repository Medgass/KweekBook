import { useState, useEffect } from 'react';
import { HomePage } from './components/HomePage';
import { BookList } from './components/BookList';
import { BookDetail } from './components/BookDetail';
import { Login } from './components/Login';
import { UserProfile } from './components/UserProfile';
import { booksData, Book } from './data/books';

type View = 'home' | 'login' | 'list' | 'detail' | 'profile';

interface User {
  name: string;
  email: string;
}

function App() {
  const [currentView, setCurrentView] = useState<View>('login');
  const [selectedBook, setSelectedBook] = useState<Book | null>(null);
  const [favorites, setFavorites] = useState<number[]>([]);
  const [isDarkMode, setIsDarkMode] = useState(false);
  const [user, setUser] = useState<User | null>(null);

  // Load user data and favorites from localStorage
  useEffect(() => {
    const savedUser = localStorage.getItem('bookUser');
    if (savedUser) {
      setUser(JSON.parse(savedUser));
      setCurrentView('home');
    }

    const savedFavorites = localStorage.getItem('bookFavorites');
    if (savedFavorites) {
      setFavorites(JSON.parse(savedFavorites));
    }

    const savedTheme = localStorage.getItem('theme');
    if (savedTheme === 'dark') {
      setIsDarkMode(true);
      document.documentElement.classList.add('dark');
    }
  }, []);

  // Handle login
  const handleLogin = (userData: User) => {
    setUser(userData);
    localStorage.setItem('bookUser', JSON.stringify(userData));
    setCurrentView('home');
  };

  // Handle logout
  const handleLogout = () => {
    setUser(null);
    localStorage.removeItem('bookUser');
    setCurrentView('login');
  };

  // Save favorites to localStorage
  const toggleFavorite = (bookId: number) => {
    setFavorites(prev => {
      const newFavorites = prev.includes(bookId)
        ? prev.filter(id => id !== bookId)
        : [...prev, bookId];
      
      localStorage.setItem('bookFavorites', JSON.stringify(newFavorites));
      return newFavorites;
    });
  };

  // Toggle dark mode
  const toggleDarkMode = () => {
    setIsDarkMode(prev => {
      const newMode = !prev;
      if (newMode) {
        document.documentElement.classList.add('dark');
        localStorage.setItem('theme', 'dark');
      } else {
        document.documentElement.classList.remove('dark');
        localStorage.setItem('theme', 'light');
      }
      return newMode;
    });
  };

  const handleEnterLibrary = () => {
    setCurrentView('list');
  };

  const handleSelectBook = (book: Book) => {
    setSelectedBook(book);
    setCurrentView('detail');
  };

  const handleBackToList = () => {
    setCurrentView('list');
    setSelectedBook(null);
  };

  const handleProfileClick = () => {
    setCurrentView('profile');
  };

  const handleBackFromProfile = () => {
    setCurrentView('list');
  };

  return (
    <div className="min-h-screen">
      {currentView === 'login' && (
        <Login onLogin={handleLogin} />
      )}

      {currentView === 'home' && (
        <HomePage onEnter={handleEnterLibrary} />
      )}
      
      {currentView === 'list' && (
        <BookList
          books={booksData}
          favorites={favorites}
          onToggleFavorite={toggleFavorite}
          onSelectBook={handleSelectBook}
          isDarkMode={isDarkMode}
          onToggleDarkMode={toggleDarkMode}
          onProfileClick={handleProfileClick}
          onLogout={handleLogout}
          userName={user?.name || ''}
        />
      )}
      
      {currentView === 'detail' && selectedBook && (
        <BookDetail
          book={selectedBook}
          isFavorite={favorites.includes(selectedBook.id)}
          onToggleFavorite={toggleFavorite}
          onBack={handleBackToList}
        />
      )}

      {currentView === 'profile' && user && (
        <UserProfile
          user={user}
          favorites={favorites}
          books={booksData}
          onBack={handleBackFromProfile}
          onLogout={handleLogout}
          onSelectBook={handleSelectBook}
        />
      )}
    </div>
  );
}

export default App;