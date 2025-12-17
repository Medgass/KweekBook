export interface Book {
  id: number;
  title: string;
  author: string;
  image: string;
  description: string;
  year: number;
  genre: string;
  pages: number;
  isbn: string;
  rating: number;
  language: string;
  publisher: string;
  status: 'available' | 'borrowed' | 'reserved';
  category: string;
  // Informations d'emprunt
  borrowedBy?: string;
  borrowDate?: string;
  returnDate?: string;
  maxBorrowDays: number;
  borrowHistory: {
    borrower: string;
    borrowDate: string;
    returnDate: string;
    returned: boolean;
  }[];
  reservedBy?: string;
  reservedDate?: string;
  copies: {
    total: number;
    available: number;
  };
}

export const booksData: Book[] = [
  {
    id: 1,
    title: "Les Misérables",
    author: "Victor Hugo",
    image: "https://images.unsplash.com/photo-1763768861268-cb6b54173dbf?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxib29rJTIwY292ZXIlMjBjbGFzc2ljfGVufDF8fHx8MTc2NTg3NDk1M3ww&ixlib=rb-4.1.0&q=80&w=1080",
    description: "Un roman historique et social qui suit l'histoire de Jean Valjean, un ancien forçat cherchant la rédemption dans la France du XIXe siècle.",
    year: 1862,
    genre: "Roman historique",
    pages: 1232,
    isbn: "978-2253096337",
    rating: 4.8,
    language: "Français",
    publisher: "Livre de Poche",
    status: 'available',
    category: 'Classiques',
    maxBorrowDays: 30,
    borrowHistory: [
      {
        borrower: "Lucas Bernard",
        borrowDate: "2024-10-01",
        returnDate: "2024-10-28",
        returned: true
      },
      {
        borrower: "Claire Moreau",
        borrowDate: "2024-09-05",
        returnDate: "2024-10-02",
        returned: true
      }
    ],
    copies: {
      total: 5,
      available: 5
    }
  },
  {
    id: 2,
    title: "L'Étranger",
    author: "Albert Camus",
    image: "https://images.unsplash.com/photo-1758803184789-a5dd872fe82e?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxib29rJTIwY292ZXIlMjBtb2Rlcm58ZW58MXx8fHwxNzY1ODY4NDEyfDA&ixlib=rb-4.1.0&q=80&w=1080",
    description: "L'histoire de Meursault, un homme indifférent qui commet un meurtre absurde sur une plage algérienne.",
    year: 1942,
    genre: "Roman philosophique",
    pages: 186,
    isbn: "978-2070360024",
    rating: 4.5,
    language: "Français",
    publisher: "Gallimard",
    status: 'borrowed',
    category: 'Philosophie',
    borrowedBy: "Marie Dubois",
    borrowDate: "2024-12-05",
    returnDate: "2024-12-19",
    maxBorrowDays: 14,
    borrowHistory: [
      {
        borrower: "Jean Martin",
        borrowDate: "2024-11-15",
        returnDate: "2024-11-29",
        returned: true
      },
      {
        borrower: "Sophie Lambert",
        borrowDate: "2024-10-20",
        returnDate: "2024-11-03",
        returned: true
      }
    ],
    copies: {
      total: 3,
      available: 2
    }
  },
  {
    id: 3,
    title: "Le Petit Prince",
    author: "Antoine de Saint-Exupéry",
    image: "https://images.unsplash.com/photo-1551300317-58b878a9ff6e?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxib29rJTIwY292ZXIlMjB2aW50YWdlfGVufDF8fHx8MTc2NTg2NDcxNHww&ixlib=rb-4.1.0&q=80&w=1080",
    description: "Un conte poétique et philosophique qui raconte la rencontre entre un aviateur et un jeune prince venu d'une autre planète.",
    year: 1943,
    genre: "Conte philosophique",
    pages: 96,
    isbn: "978-2070408504",
    rating: 4.9,
    language: "Français",
    publisher: "Gallimard",
    status: 'available',
    category: 'Jeunesse',
    maxBorrowDays: 21,
    borrowHistory: [
      {
        borrower: "Amélie Durand",
        borrowDate: "2024-11-10",
        returnDate: "2024-11-30",
        returned: true
      },
      {
        borrower: "Nicolas Roux",
        borrowDate: "2024-10-15",
        returnDate: "2024-11-05",
        returned: true
      },
      {
        borrower: "Julie Simon",
        borrowDate: "2024-09-20",
        returnDate: "2024-10-10",
        returned: true
      }
    ],
    copies: {
      total: 4,
      available: 4
    }
  },
  {
    id: 4,
    title: "Madame Bovary",
    author: "Gustave Flaubert",
    image: "https://images.unsplash.com/photo-1758796629109-4f38e9374f45?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxib29rJTIwY292ZXIlMjBmaWN0aW9ufGVufDF8fHx8MTc2NTg0NjYwMXww&ixlib=rb-4.1.0&q=80&w=1080",
    description: "Le portrait d'Emma Bovary, une jeune femme provinciale insatisfaite qui cherche l'amour passionné dans les romans romantiques.",
    year: 1857,
    genre: "Roman réaliste",
    pages: 464,
    isbn: "978-2070413119",
    rating: 4.3,
    language: "Français",
    publisher: "Gallimard",
    status: 'available',
    category: 'Classiques',
    maxBorrowDays: 30,
    borrowHistory: [
      {
        borrower: "Pierre Blanc",
        borrowDate: "2024-10-10",
        returnDate: "2024-11-08",
        returned: true
      }
    ],
    copies: {
      total: 6,
      available: 6
    }
  },
  {
    id: 5,
    title: "Le Rouge et le Noir",
    author: "Stendhal",
    image: "https://images.unsplash.com/photo-1763768861268-cb6b54173dbf?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxib29rJTIwY292ZXIlMjBsaXRlcmF0dXJlfGVufDF8fHx8MTc2NTg3NTIwMnww&ixlib=rb-4.1.0&q=80&w=1080",
    description: "L'ascension sociale de Julien Sorel, un jeune ambitieux dans la France de la Restauration.",
    year: 1830,
    genre: "Roman d'apprentissage",
    pages: 712,
    isbn: "978-2253098201",
    rating: 4.4,
    language: "Français",
    publisher: "Livre de Poche",
    status: 'reserved',
    category: 'Classiques',
    reservedBy: "Thomas Rousseau",
    reservedDate: "2024-12-14",
    maxBorrowDays: 30,
    borrowHistory: [
      {
        borrower: "Émilie Petit",
        borrowDate: "2024-11-01",
        returnDate: "2024-11-25",
        returned: true
      }
    ],
    copies: {
      total: 2,
      available: 1
    }
  },
  {
    id: 6,
    title: "Les Fleurs du mal",
    author: "Charles Baudelaire",
    image: "https://images.unsplash.com/photo-1716892001590-79a5b6207662?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxib29rJTIwY292ZXIlMjBwb2V0cnl8ZW58MXx8fHwxNzY1ODc2MTUxfDA&ixlib=rb-4.1.0&q=80&w=1080",
    description: "Un recueil de poèmes qui explore les thèmes de la beauté, du mal, de la décadence et de l'amour.",
    year: 1857,
    genre: "Poésie",
    pages: 352,
    isbn: "978-2253006534",
    rating: 4.7,
    language: "Français",
    publisher: "Livre de Poche",
    status: 'available',
    category: 'Poésie',
    maxBorrowDays: 21,
    borrowHistory: [
      {
        borrower: "Isabelle Garnier",
        borrowDate: "2024-11-20",
        returnDate: "2024-12-10",
        returned: true
      }
    ],
    copies: {
      total: 3,
      available: 3
    }
  },
  {
    id: 7,
    title: "Germinal",
    author: "Émile Zola",
    image: "https://images.unsplash.com/photo-1763768861268-cb6b54173dbf?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxib29rJTIwY292ZXIlMjBjbGFzc2ljfGVufDF8fHx8MTc2NTg3NDk1M3ww&ixlib=rb-4.1.0&q=80&w=1080",
    description: "Une fresque sociale sur les conditions de vie des mineurs dans le nord de la France au XIXe siècle.",
    year: 1885,
    genre: "Roman naturaliste",
    pages: 592,
    isbn: "978-2253004226",
    rating: 4.6,
    language: "Français",
    publisher: "Livre de Poche",
    status: 'available',
    category: 'Classiques',
    maxBorrowDays: 30,
    borrowHistory: [
      {
        borrower: "François Leroy",
        borrowDate: "2024-10-25",
        returnDate: "2024-11-22",
        returned: true
      },
      {
        borrower: "Mathilde Girard",
        borrowDate: "2024-09-15",
        returnDate: "2024-10-12",
        returned: true
      }
    ],
    copies: {
      total: 4,
      available: 4
    }
  },
  {
    id: 8,
    title: "Candide",
    author: "Voltaire",
    image: "https://images.unsplash.com/photo-1758803184789-a5dd872fe82e?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxib29rJTIwY292ZXIlMjBtb2Rlcm58ZW58MXx8fHwxNzY1ODY4NDEyfDA&ixlib=rb-4.1.0&q=80&w=1080",
    description: "Un conte philosophique satirique qui critique l'optimisme et la société du XVIIIe siècle.",
    year: 1759,
    genre: "Conte philosophique",
    pages: 160,
    isbn: "978-2253006329",
    rating: 4.5,
    language: "Français",
    publisher: "Livre de Poche",
    status: 'available',
    category: 'Philosophie',
    maxBorrowDays: 14,
    borrowHistory: [
      {
        borrower: "Alexandre Mercier",
        borrowDate: "2024-11-05",
        returnDate: "2024-11-19",
        returned: true
      }
    ],
    copies: {
      total: 2,
      available: 2
    }
  }
];