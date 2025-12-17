import { Button } from './ui/button';
import logo from 'figma:asset/c7a0325c9d08e8026b78e7ce8f58f07a64c11072.png';

interface HomePageProps {
  onEnter: () => void;
}

export function HomePage({ onEnter }: HomePageProps) {
  return (
    <div className="min-h-screen flex items-center justify-center bg-gradient-to-br from-background via-background to-secondary">
      <div className="text-center space-y-8 px-4">
        <div className="flex justify-center">
          <div className="relative">
            <div className="absolute inset-0 bg-gradient-to-r from-primary to-accent blur-3xl opacity-30 rounded-full animate-pulse"></div>
            <img src={logo} alt="KweekBook" className="w-48 h-48 relative object-contain animate-bounce" style={{ animationDuration: '3s' }} />
          </div>
        </div>
        <div className="space-y-2">
          <h1 className="text-5xl md:text-6xl tracking-tight bg-gradient-to-r from-primary to-accent bg-clip-text text-transparent">KweekBook</h1>
          <p className="text-xl text-muted-foreground">Découvrez votre prochaine lecture</p>
        </div>
        <Button 
          onClick={onEnter}
          className="bg-gradient-to-r from-primary to-accent hover:from-primary/90 hover:to-accent/90 text-white px-8 py-6 rounded-lg transition-all duration-300 shadow-lg shadow-accent/20 hover:shadow-xl hover:shadow-accent/30 hover:scale-105"
        >
          Entrer dans la bibliothèque
        </Button>
      </div>
    </div>
  );
}